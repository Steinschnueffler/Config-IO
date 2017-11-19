package linus.io.config;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.CharBuffer;

import linus.io.config.configs.MultipleStringConfig;
import linus.io.config.configs.SingleStringConfig;
import linus.io.config.exception.ConfigWriteEexception;
import linus.io.config.exception.GeneratedConfigException;
import linus.io.config.exception.InvalidConfigException;
import linus.io.config.io.ComplexConfigReader;
import linus.io.config.io.ComplexConfigWriter;
import linus.io.config.io.AbstractConfigReader;
import linus.io.config.io.ConfigWriter;
import linus.io.config.io.SerializableConfigData;
import linus.io.config.io.SerializingConfigWriter;
import linus.io.config.io.ThreadedConfigWriter;
import linus.io.config.util.ConfigComparator;
import linus.io.config.util.ConfigFile;
import linus.io.config.util.ConfigHolder;

/**
 * This abstract class is the root of all Configs and it or any extend of it can
 * be written by {@link ConfigWriter}s and readed by {@link AbstractConfigReader}s <br>
 * It should be used to make own Configs with own way of writing and reading.
 * The Classes wich extends from this and should be readed by ConfigReader
 * <b>MUST</b> be default Constructable because oft the use of reflections to
 * return exactly this class. This provides to have a lot of different config
 * types in one file. (Empty Constructor).
 *
 * @author Linus Dierheimer
 * @param <E>
 *            - The type of the value of the Config
 * @see SingleConfig
 * @see MultipleConfig
 * @see AbstractConfigReader
 * @see ConfigWriter
 * @see ConfigComparator
 * @see ConfigFile
 * @version 1.0
 * @since java 1.5 (because of the use of generic)
 *
 */
public abstract class Config<E> extends ConfigBase implements Cloneable, Comparable<Config<?>>, Readable {

	// static

	/**
	 * This is the default name of a Config, which it will have if it wasn't set. It
	 * is implemented by "unknown_name".
	 */
	public static final String DEFAULT_NAME = "unknown_name";

	/**
	 * This is the default Value of a Config, which it will have if it wasn't set.
	 * It is implemented by null.
	 */
	public static final Object DEFAULT_VALUE = null;

	/**
	 * This Config represents an Empty Config with the {@link #DEFAULT_NAME} and the
	 * {@link #DEFAULT_VALUE}. It's write method will return a String array with the
	 * length of 0 and the read Method does nothing except of returning itself.
	 */
	public static final Config<Object> EMPTY_CONFIG = new Config<>() {
		
		@Override
		public Config<Object> read(String[] lines) {
			return this;
		}
		
		@Override
		public String[] write() {
			return super.write();
		}
	};

	/**
	 * This char is the standart Separator for Name and Value. It is used by all
	 * Standart Config Implementations.
	 */
	public static final char SEPARATOR = '=';

	/**
	 * Trys to return the right Config out of the readed lines. returns null if
	 * readedLines is null or has a length of 0.
	 *
	 * @param readedLines
	 * @return a fitting Config
	 */
	@SuppressWarnings("unchecked")
	public static <E extends ConfigBase> E getConfig(String[] readedLines) {
		if (readedLines.length == 0)
			return null;
		if (readedLines.length == 1)
			return (E) SingleConfig.getSingleConfig(readedLines[0]);
		return (E) MultipleConfig.getMultipleConfig(readedLines);
	}

	// Config

	/**
	 * This Object is for multithreading access. Critical parts a surrounded by
	 * synchronized like this: <blockquote> <code>
	 * synchronized(lock){
	 * 	<dd>//action</dd>
	 * }
	 * </code> </blockquote>
	 */
	protected final Object lock = new Object();

	/**
	 * The Name value of a Config. If a Config is Constucted with a default
	 * Constructor it will be the String "unknown_name". Calling the
	 * {@link #read(String[])} method should change the value.
	 */
	protected String name = DEFAULT_NAME;

	/**
	 * The Value of this Config. If this field is null, the Config handles as it
	 * does't has any Value. This will happen for example by calling the default
	 * Constructor. Calling the {@link #read(String[])} method should change the
	 * value.
	 * 
	 */
	@SuppressWarnings("unchecked")
	protected E value = (E) DEFAULT_VALUE;

	/**
	 * Creates a standart abstract Config with the name of "unknown_name" and a
	 * value of null
	 */
	public Config() {
	}

	/**
	 * Constructs a Config with the same name and value like them of the given Config.
	 * 
	 * @param cfg the Config which values should be copied.
	 */
	public Config(Config<E> cfg) {
		setValues(cfg);
	}

	/**
	 * Creates a new Config with the given Value and a Name of "unknown_name". If
	 * the Value is a String the Constructor actually sets the Name and not the
	 * value. To provide this the Constructor {@link #Config(String, Object)} can be
	 * called with the wanted value and the {@link #DEFAULT_NAME} :
	 * {@code Config(Config.DEFAULT_NAME, "your Name");}.
	 * 
	 * @param value
	 *            the value of the Config
	 */
	public Config(E value) {
		setValue(value);
	}

	/**
	 * Creates a new Config with the given Name and a value of null.
	 * 
	 * @param name
	 *            the name of the Config
	 */
	public Config(String name) {
		setName(name);
	}
	
	/**
	 * Constructs a Config like the default Constructor, it only sets the Value of
	 * {@link #name}
	 * 
	 * to the given String.
	 * 
	 * @param name
	 *            the Name of the Config
	 * @param value
	 *            the value of the Config
	 */
	public Config(String name, E value) {
		setValues(name, value);
	}

	/**
	 * Adds this Config to the given {@link ConfigHolder} and returns it.
	 * 
	 * @param holder
	 * @return
	 */
	public ConfigHolder addToHolder(ConfigHolder holder) {
		if (holder == null)
			return newHolder();
		synchronized (lock) {
			holder.addConfig(this);
		}
		return holder;
	}

	/**
	 * Clones the Config. This means that it gives back a brand new Config, but with
	 * the class, the Name and the Value of the old one.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Config<E> clone() throws CloneNotSupportedException {
		synchronized (lock) {
			return (Config<E>) super.clone();
		}
	}

	/**
	 * Compares the Config to any other Config. First it Sorts after the
	 * {@link ConfigType}, then after the Class of the Value (alphabetic) and then
	 * after the Name. If all this is equal it trys to compare the Value. if even
	 * this is equal it returns 0.
	 */
	@Override
	public int compareTo(Config<?> o) {
		Config<?> cfg;
		synchronized (lock) {
			try {
				cfg = clone();
			} catch (CloneNotSupportedException e) {
				synchronized (lock) {
					return ConfigComparator.compareStatic(this, o);
				}
			}
		}
		synchronized (cfg.lock) {
			return ConfigComparator.compareStatic(cfg, o);
		}
	}

	/**
	 * This method creates an {@link InvalidConfigException} to this Config. It can
	 * be used to throw if this Config isn't supported or a wrong type.
	 *
	 * @return a InvalidConfigException to this Config
	 */
	public InvalidConfigException createException() {
		return createException("");
	}

	/**
	 * This method creates an {@link InvalidConfigException} to this Config. It can
	 * be used to throw if this Config isn't supported or a wrong type. The Info
	 * will be the given String.
	 *
	 * @return a InvalidConfigException to this Config
	 */
	public InvalidConfigException createException(String msg) {
		String str;
		synchronized (lock) {
			str = toString();
		}
		return new InvalidConfigException(str, new GeneratedConfigException(msg));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Config))
			return false;
		Config<?> other = (Config<?>) obj;
		synchronized (lock) {
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
		}
		return true;
	}

	/**
	 * Config musn't be finalized
	 */
	@Override
	@Deprecated
	protected void finalize() throws Throwable {
		try {
			throw new Throwable("Can't finalize a Config");
		} finally {
			super.finalize();
		}
	}

	/**
	 * Returns the actual {@link ConfigType}, directed tested at calling this
	 * method, so it can change while handling with this Config. If value is null,
	 * it returns {@link ConfigType#Null}. If it is an Array, it returns
	 * {@link ConfigType#Multiple}. If the class of the Value is an Enum, it return
	 * {@link ConfigType#Custom}. Otherwise it returns {@link ConfigType#Single}.
	 * 
	 * @return the actual ConfigStat
	 */
	public ConfigStat getConfigStat() {
		if (isEmpty())
			return ConfigStat.Empty;
		if (!hasName())
			return ConfigStat.Value;
		if (!hasValue())
			return ConfigStat.Name;
		return ConfigStat.Normal;
	}

	/**
	 * Returns the ConfigType of a Config. This can be used to sort or handle right
	 * with them. Usually its Single or Multiple
	 *
	 * @return the ConfigType of a Config
	 */
	public ConfigType getConfigType() {
		return ConfigType.Custom;
	}

	/**
	 * This class is used for getting the name of the Config and by
	 * {@link ConfigWriter} to write it to a file. This method should never return
	 * null, so the name should be in the Array of the read and write method and, if
	 * it existst, in a Constructor. Note that there still must be a empty
	 * Constructor.
	 * 
	 * @return the Name of the Config
	 */
	public String getName() {
		synchronized (lock) {
			return name;
		}
	}

	/**
	 * This method is for use after reading the class to get the Value. This can be
	 * any Object of the class given by the generic parameter of this class
	 *
	 * @return the Value of this Config
	 */
	public E getValue() {
		synchronized (lock) {
			return value;
		}
	}

	/**
	 * This method returns the Value of a Config as a String representation. In the
	 * standart implementation it is <code>{@link #getValue()}.toString() </code>.
	 *
	 * @return a String representation of the Value
	 */
	public String getValueAsString() {
		Object obj = getValue();
		return obj == null ? "null" : obj.toString();
	}

	/**
	 * Because of the Overriding of {@link #equals(Object)} this Method is also
	 * Overriden to have a safer handle hat HashContainers. It simply returns
	 * super.hashCode * 13. If this would be higer than Integer.MAX_VALUE it returns
	 * super.hashCode / 13.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 0;
		synchronized (lock) {
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
		}
		return result;
	}

	/**
	 * Testes if this Config has a setted Name. This means that it looks if the
	 * value is null or default ("unknown_name"). Otherwise it returns true. The
	 * Name can be setted by using the right Constructor, calling the
	 * {@link #read(SerializableConfigData)} or the {@link #read(String[])} method.
	 * 
	 * @return true if the Name was setted
	 */
	public boolean hasName() {
		String name = getName();
		if (name == null || name.equalsIgnoreCase("unknown_name"))
			return false;
		return true;
	}

	/**
	 * Testes if this Config contains a setted value. This means that it looks if
	 * the Value is default (null) or was changed. The Value can be setted by using
	 * the right Constructor, calling the {@link #read(SerializableConfigData)} or
	 * the {@link #read(String[])} method.
	 * 
	 * @return true if the value isn't null
	 */
	public boolean hasValue() {
		return getValue() != null;
	}

	/**
	 * Testes if this Config doesn't has a name AND does't has a value.
	 * So it will return false if {@link #hasName()} or / and {@link #hasName()} 
	 * returns true.
	 * 
	 * @return true if the Config doesn't has a name AND doesn't has a Value.
	 */
	public boolean isEmpty() {
		return hasValue() || hasName();
	}

	/**
	 * Returns a new {@link ConfigHolder} holding only this Config. Further Configs
	 * can be added to it.
	 * 
	 * @return a new ConfigHolder
	 */
	public ConfigHolder newHolder() {
		Config<E> cfg;
		synchronized (lock) {
			cfg = this;
		}
		return new ConfigHolder(cfg);
	}

	/**
	 * Normalizes this Config by trying to convert it to a Config of a primitive
	 * Type. It also makes it, if necesarry and possible, to an Instance of
	 * {@link SingleConfig} or {@link MultipleConfig}.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Config<?> normalize() {
		if(isEmpty()) return EMPTY_CONFIG;
		String name = getName();
		E value = getValue();
		if(value == null) return new Config<E>(name, (E) DEFAULT_VALUE) {};
		if(name == null) return new Config<E>(DEFAULT_NAME, value) {};
		if(value.getClass().isArray()) {
			return MultipleConfig.getMultipleConfig(write());
		}
		else return SingleConfig.getSingleConfig(getName(), getValueAsString());
	}

	@Override
	public int read(CharBuffer cb) throws IOException {
		String append;
		synchronized (lock) {
			append = toString();
		}
		return cb.put(append).length();
	}

	/**
	 * Reads a Config from a {@link SerializableConfigData}. This should change the
	 * Value and the Name and the Value of this Config. Calling this metod should be
	 * equal as calling this class with the Constructor
	 * {@code Config(SerializableConfigData data)}.
	 * 
	 * @param data
	 *            the data to be readed
	 */
	public Config<E> read(SerializableConfigData<E> data) {
		this.name = data.name;
		this.value = data.value;
		return this;
	}

	/**
	 * This method is used by {@link AbstractConfigReader} to read this Config. Each String
	 * in the given Array represents a line in the File. Using this method should be
	 * equal as initializing this class with a Constructor.
	 *
	 * @param lines
	 *            - the readed lines
	 */
	@SuppressWarnings("unchecked")
	public Config<E> read(String[] lines){
		Config<?> cfg;
		if(lines.length == 0)
			cfg = EMPTY_CONFIG;
		else if(lines.length == 1)
			cfg = SingleConfig.getSingleConfig(lines[0]);
		else cfg = MultipleConfig.getMultipleConfig(lines);
		return setValues((Config<E>) cfg);
	}

	/**
	 * sets the value of the name to the given String.
	 * 
	 * @param name the new Name of the Config
	 * @return itself
	 */
	public Config<E> setName(String name) {
		synchronized (lock) {
			this.name = name;
		}
		return this;
	}

	/**
	 * sets the value to the given Object.
	 * 
	 * @param value the new Value of the Config
	 * @return itself
	 */
	public Config<E> setValue(E value) {
		synchronized (lock) {
			this.value = value;
		}
		return this;
	}

	/**
	 * Copys the value and the of the given Config and sets the own ones to it.
	 * 
	 * @param cfg the Config thet should be copied.
	 * @return itself
	 */
	public Config<E> setValues(Config<E> cfg){
		setValues(cfg.getName(), cfg.getValue());
		return this;
	}

	/**
	 * Sets the name and the value tot given name and value.
	 *  
	 * @param name the new Name of the Config
	 * @param value the new Value of the Config
	 * @return itself
	 */
	public Config<E> setValues(String name, E value){
		setName(name);
		setValue(value);
		return this;
	}
	
	/**
	 * returns a {@link SerializableConfigData} of the Config with the same values.
	 * This is used by {@link SerializingConfigWriter} to write the Config compact.
	 * It can be converted back to a Config by {@link #read(SerializableConfigData)}
	 * 
	 * @return
	 */
	public SerializableConfigData<E> toSerializableConfig() {
		return new SerializableConfigData<E>(getName(), getValue(), getClass().getName());
	}

	/**
	 * This method is the standart implementation of toString by Config. It simply
	 * returns <br>
	 * <code>getName() + " = " + getValue().toString</code>
	 *
	 * @return a String representation of this config
	 */
	@Override
	public String toString() {
		return getName() + " " + SEPARATOR + " " + getValueAsString();
	}

	/**
	 * Gives back a Config which has the same name and the value as String, so it
	 * can be used universally. At {@link SingleConfig} it is normally
	 * {@link SingleStringConfig} and at {@link MultipleConfig} it is usually
	 * {@link MultipleStringConfig}. In the standart implementation by these two,
	 * converting to String works easally over the toString() method.
	 * 
	 * @return a String Config with the same name and the Value as a String
	 */
	public Config<? extends Object> toStringConfig() {
		return new SingleStringConfig(getName(), getValueAsString());
	}

	/**
	 * This method is used by {@link ComplexConfigWriter} to write this Config to a
	 * file. Each String in the Array is a new Line. The {@link ComplexConfigReader}
	 * gives exactly this Array to the read method at reading if it wasn't changed
	 * by the user.
	 *
	 * @return an array of String that should be written
	 */
	public String[] write() {
		return new String[]{getName() + " " + SEPARATOR + " " + getValueAsString()};
	}

	/**
	 * This method executes the given {@link ConfigWriter} to write itself. Calling
	 * this is equal to <code>writer.writeConfig(thisConfig)</code>.
	 *
	 * @throws NullPointerException
	 *             if writer is null
	 * @param writer
	 *            - the writer that should write this Config
	 * @throws ConfigWriteEexception
	 *             if the writer throws a {@link ConfigWriteEexception}
	 */
	public void writeTo(ConfigWriter writer) throws ConfigWriteEexception {
		Config<E> cfg;
		try {
			cfg = clone();
		} catch (CloneNotSupportedException e) {
			synchronized (lock) {
				writer.writeConfig(this);
			}
			return;
		}
		synchronized (cfg.lock) {
			writer.writeConfig(cfg);
		}
	}

	/**
	 * Writes the Config to the given {@link OutputStream}. The standart implemented
	 * is <code>
	 * out.write(toString().getBytes());
	 * </code>.
	 * 
	 * @param out
	 *            the OutputStream
	 * @throws IOException
	 *             if a IOException occures
	 */
	public void writeTo(OutputStream out) throws IOException {
		String[] lines;
		synchronized (lock) {
			lines = write();
		}
		for (String s : lines)
			out.write(s.getBytes());
	}
	
	/**
	 * This method behaves as {@link #writeTo(ConfigWriter)}, only with the
	 * different that the given writer is a {@link ThreadedConfigWriter}.
	 *
	 * @throws NullPointerException
	 *             if writer is null
	 * @param writer
	 *            - the writer that should write this Config
	 */
	public void writeTo(ThreadedConfigWriter writer) {
		Config<E> cfg;
		try {
			cfg = clone();
		} catch (CloneNotSupportedException e) {
			synchronized (lock) {
				writer.writeConfig(this);
			}
			return;
		}
		synchronized (cfg.lock) {
			writer.writeConfig(cfg);
		}
	}

	/**
	 * Writes the Config to the given {@link Writer}. The standart implemented is
	 * <code>
	 * writer.write(toString());
	 * </code>.
	 * 
	 * @param out
	 *            the OutputStream
	 * @throws IOException
	 *             if a IOException occures
	 */
	public void writeTo(Writer writer) throws IOException {
		String[] lines;
		synchronized (lock) {
			lines = write();
		}
		for (String s : lines)
			writer.write(s);
	}
	
}
