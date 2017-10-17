package linus.io.config;

/**
 * This interface is used by {@link ComplexConfigWriter} and {@link ComplexConfigReader} to
 * get the chars and Strings that marks something in a File. A default implementation
 * is the {@link ConfigIOCharsAdapter}
 *
 * @author Linus Dierheimer
 * @see ConfigIOCharsAdapter
 * @since java 1.5
 * @version 1.0
 *
 */
public interface ConfigIOChars {

	/**
	 *This Method is used by {@link ComplexConfigReader} and {@link ComplexConfigWriter} to mark that a
	 *new Config class is starting
	 *
	 * @return the char with that a new Config in a File start
	 */
	public abstract char getClassStart();

	/**
	 * This Method is used by {@link ComplexConfigWriter} to mark that a line is a comment.
	 * Lines starting with a character returned by this Method will be ignored by {@link ComplexConfigReader}
	 * @return the char with that a comment starts
	 */
	public abstract char getInfoStart();

	/**
	 * This method is used by {@link ComplexConfigReader} and {@link ComplexConfigWriter} to mark that a Config
	 * class is over. I the fill the will be classStart character + this String
	 * @return
	 */
	public abstract String getClassEnd();

	/**
	 * returns the default {@link ConfigIOChars} implementation of {@link ConfigIOCharsAdapter}.
	 * Calling this method is equal to using {@code new ConfigIOCharsAdapter()}
	 * @return
	 */
	public static ConfigIOChars getDefault(){
		return ConfigIOCharsBuilder.getDefault();
	}
}
