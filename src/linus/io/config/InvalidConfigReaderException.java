package linus.io.config;

/**
 * This {@link Exception} marks that a {@link ConfigReader} is invalid or
 * unsuported. A detail Exception can be get over ConfigReader.createException().
 *
 * @author Linus Dierheimer
 * @since java 1.4
 * @version 1.0
 * @see ConfigReader
 *
 */
public class InvalidConfigReaderException extends IllegalArgumentException{
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new IvalidConfigReaderException with the given String as Massage.
	 * @param str
	 */
	public InvalidConfigReaderException(String str) {
		super(str);
	}

	InvalidConfigReaderException(ConfigReader cr, Throwable th) {
		super("Invalid ConfigReader: " +cr.toString(), th);
	}

}
