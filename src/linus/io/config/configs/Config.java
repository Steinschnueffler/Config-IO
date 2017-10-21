package linus.io.config.configs;

import linus.io.config.ComplexConfigReader;
import linus.io.config.ComplexConfigWriter;
import linus.io.config.ConfigReader;
import linus.io.config.ConfigType;
import linus.io.config.ConfigWriter;

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
public abstract class Config<E> extends ConfigBase implements Cloneable{

	/**
	 * This char is the standart Separator for Name and Value. It is used
	 * by all Standart Config Implementations.
	 */
	public static final char SEPARATOR = '=';

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
	 * This method is used by {@link ComplexConfigReader} to read this Config.
	 *Each String in the given Array represents a line in the File. Using this
	 *method should be equal as initializing this class with a Constructor.
	 *
	 * @param lines - the readed lines
	 */
	public abstract Config<E> read(String[] lines);

	/**
	 * This class is used for getting the name of the Config and by
	 * {@link ComplexConfigWriter} to write it to a file. This method should never return null,
	 * so the name should be in the Array of the read and write method and, if it existst,
	 * in a Constructor. Note that there still must be a empty Constructor.
	 * @return the Name of the Config
	 */
	public abstract String getName();

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

	@SuppressWarnings("unchecked")
	@Override
	public Config<E> clone() throws CloneNotSupportedException{
		return (Config<E>) super.clone();
	}

}
