package linus.io.config;

import linus.io.config.configs.MultipleStringConfig;
import linus.io.config.configs.SingleStringConfig;

/**
 *This abstract class is the root of all Configs and it or
 *any extend of it can be written by {@link ConfigWriter}s and
 *readed by {@link ConfigReader}s <br>
 *It should be used to make own Configs with own way of writing
 *and reading. The Classes wich extends from this and should be readed by
 *ConfigReader <b>MUST</b> be default Constructable because oft the use of
 *reflections to return exactly this class. This provides to have a lot of different
 *config types in one file.
 *(Empty Constructor).
 *
 * @author Linus Dierheimer
 * @param <E> - The type of the value of the Config
 * @see SingleConfig
 * @see MultipleConfig
 * @see RandomConfig
 * @version 1.0
 * @since java 1.5 (because of the use of generic)
 *
 */
public abstract class Config<E> extends ConfigBase implements Cloneable, Comparable<Config<?>>{

	/**
	 * Creates a standart abstract Config
	 */
	public Config() {}

	/**
	 * This char is the standart Separator for Name and Value. It is used
	 * by all Standart Config Implementations.
	 */
	public static final char SEPARATOR = '=';

	/**
	 * The Name value of a Config. If a Config is Constucted with a default Constructor
	 * it will be a String with a length of 0. Calling the {@link #read(String[])} method
	 * should change the value.
	 */
	protected String name = "unknown_name";

	/**
	 * Constructs a Config like the default Constructor, it only sets the Value of {@link #name}
	 * to the given String.
	 * @param name the Name of the Config
	 */
	public Config(String name){
		this.name = name;
	}

	/**
	 *This method is for use after reading the class to get the Value.
	 *This can be any Object of the class given by the generic parameter
	 *of this class
	 *
	 * @return the Value of this Config
	 */
	public abstract E getValue();

	/**
	 *This method is used by {@link ComplexConfigWriter} to write this Config
	 *to a file. Each String in the Array is a new Line. The {@link ComplexConfigReader}
	 *gives exactly this Array to the read method at reading if it
	 *wasn't changed by the user.
	 *
	 * @return an array of String that should be written
	 */
	public abstract String[] write();

	/**
	 * This method is used by {@link ConfigReader} to read this Config.
	 *Each String in the given Array represents a line in the File. Using this
	 *method should be equal as initializing this class with a Constructor.
	 *
	 * @param lines - the readed lines
	 */
	public abstract Config<E> read(String[] lines);

	/**
	 * This class is used for getting the name of the Config and by
	 * {@link ConfigWriter} to write it to a file. This method should never return null,
	 * so the name should be in the Array of the read and write method and, if it existst,
	 * in a Constructor. Note that there still must be a empty Constructor.
	 * @return the Name of the Config
	 */
	public String getName(){
		return name;
	}

	/**
	 *Returns the ConfigType of a Config. This can be used
	 *to sort or handle right with them. Usually its Single or Multiple
	 *
	 * @return the ConfigType of a Config
	 */
	public abstract ConfigType getConfigType();

	/**
	 * This method is the standart implementation of toString by Config.
	 * It simply returns <br>
	 * <code>getName() + " = " + getValue().toString</code>
	 *
	 * @return a String representation of this config
	 */
	@Override
	public String toString() {
		return getName() + " = " +getValue().toString();
	}

	/**
	 * Clones the Config. This means that it gives back a brand new Config, but with the
	 * class, the Name and the Value of the old one.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Config<E> clone() throws CloneNotSupportedException{
		return (Config<E>) super.clone();
	}

	/**
	 * Testes if a Object equals this Config. First it tests if super.equals return true and
	 * if this is the case returns it.
	 * If not, it compares the generic Class, the Name and the Value. If all of them are
	 * equal it will return true.
	 */
	@Override
	public boolean equals(Object obj) {
		if(super.equals(obj)) return true;
		Config<?> cfg;
		try{
			cfg = (Config<?>) obj;
		}catch(Exception e){
			return false;
		}
		if(!getValue().getClass().equals(cfg.getValue().getClass())) return false;
		if(!getName().equals(cfg.getName())) return false;
		if(!getValue().equals(cfg.getValue())) return false;
		return true;
	}

	/**
	 * Because of the Overriding of {@link #equals(Object)} this Method is also Overriden
	 * to have a safer handle hat HashContainers. It simply returns super.hashCode * 13. If this
	 * would be higer than Integer.MAX_VALUE it returns super.hashCode / 13.
	 */
	@Override
	public int hashCode() {
		long l = super.hashCode() * 13l;
		if(l < Integer.MAX_VALUE)
			return super.hashCode() * 13;
		else
			return super.hashCode() / 13;
	}

	/**
	 * Compares the Config to any other Config. First it Sorts after the {@link ConfigType},
	 * then after the Class of the Value (alphabetic) and then after the Name. If all this
	 * is equal it trys to compare the Value. if even this is equal it returns 0.
	 */
	@Override
	public int compareTo(Config<?> o) {

		if(equals(o)) return 0;

		int typeComp = getConfigType().compareTo(o.getConfigType());
		if(typeComp != 0) return typeComp;

		if(!getValue().getClass().equals(o.getValue().getClass())){
			int simpleNameComp = compareString(getValue().getClass().getSimpleName(), o.getValue().getClass().getSimpleName());
			if(simpleNameComp != 0) return simpleNameComp;

			int classNameComp = compareString(getValue().getClass().getName(), o.getValue().getClass().getName());
			if(classNameComp != 0) return classNameComp;
		}

		int nameComp = compareString(getName(), o.getName());
		if(nameComp != 0) return nameComp;

		return compareObject(getValue(), o.getValue());
	}

	private int compareString(String o1, String o2){

		if(o1.equals(o2)) return 0;

		int max = o1.length();
		if(o2.length() < o1.length()) max = o2.length();

		for(int i = 0; i < max; i++){
			Character c = o1.charAt(i);
			if(c.compareTo(o2.charAt(i)) < 0) return -1;
			if(c.compareTo(o2.charAt(i)) > 0) return 1;
		}

		if(o1.length() < o2.length()) return -1;
		if(o1.length() > o2.length()) return 1;

		return 0;
	}

	@SuppressWarnings("unchecked")
	private int compareObject(Object o1, Object o2){
		try{
			Comparable<Object> c1 = (Comparable<Object>) o1;
			return c1.compareTo(o2);
		}catch(Exception e){}
		try{
			Comparable<Object> c2 = (Comparable<Object>) o2;
			return c2.compareTo(o1) * -1;
		}catch(Exception e){}

		return compareString(o1.toString(), o2.toString());
	}

	/**
	 * returns a {@link SerializableConfigData} of the Config with the same values.
	 * This is used by {@link SerializingConfigWriter} to write the Config compact.
	 * It can be converted back to a Config by {@link #read(SerializableConfigData)}
	 * @return
	 */
	public SerializableConfigData<E> toSerializableConfig(){
		return new SerializableConfigData<E>(getName(), getValue(), getClass().getName());
	}

	/**
	 * Reads a Config from a {@link SerializableConfigData}. This should change the Value and the Name
	 * and the Value of this Config. Calling this metod should be equal as calling this class
	 * with the Constructor {@code Config(SerializableConfigData data)}.
	 * @param data
	 */
	public abstract Config<E> read(SerializableConfigData<E> data);

	/**
	 * Gives back a Config which has the same name and the value as String, so
	 * it can be used universally. At {@link SingleConfig} it is normally
	 * {@link SingleStringConfig} and at {@link MultipleConfig} it is usually
	 * {@link MultipleStringConfig}. In the standart implementation by these two,
	 * converting to String works easally over the toString() method.
	 * @return a String Config with the same name and the Value as a String
	 */
	public abstract Config<? extends Object> toStringConfig();

	/**
	 *This method executes the given {@link ConfigWriter} to write itself.
	 *Calling this is equal to <code>writer.writeConfig(thisConfig)</code>.
	 *
	 * @throws NullPointerException if writer is null
	 * @param writer - the writer that should write this Config
	 */
	public void writeTo(ConfigWriter writer){
		writer.writeConfig(this);
	}

	/**
	 * This method behaves as {@link #writeTo(ConfigWriter)}, only with the
	 * different that the given writer is a {@link ThreadedConfigWriter}.
	 *
	 * @throws NullPointerException if writer is null
	 * @param writer - the writer that should write this Config
	 */
	public void writeTo(ThreadedConfigWriter writer){
		writer.writeConfig(this);
	}

	/**
	 * This method returns the Value of a Config as a String representation.
	 * In the standart implementation it is <code>{@link #getValue()}.toString() </code>.
	 *
	 * @return a String representation of the Value
	 */
	public String getValueAsString(){
		return getValue().toString();
	}

	/**
	 * This method creates an {@link InvalidConfigException} to this Config.
	 * It can be used to throw if this Config isn't supported or a wrong type.
	 *
	 * @return a InvalidConfigException to this Config
	 */
	public InvalidConfigException createException(){
		return new InvalidConfigException(this, new GeneratedException());
	}

	private static class GeneratedException extends Exception{
		private static final long serialVersionUID = 1L;
	}
}
