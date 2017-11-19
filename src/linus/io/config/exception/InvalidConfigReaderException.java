package linus.io.config.exception;

import linus.io.config.io.AbstractConfigReader;

/**
 * This {@link Exception} marks that a {@link AbstractConfigReader} is invalid or
 * unsuported. A detail Exception can be get over ConfigReader.createException().
 *
 * @author Linus Dierheimer
 * @since java 1.4
 * @version 1.0
 * @see AbstractConfigReader
 *
 */
public class InvalidConfigReaderException extends ConfigOperationException{
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new IvalidConfigReaderException with the given String as Massage.
	 * @param str
	 */
	public InvalidConfigReaderException(String str) {
		super(str);
	}

	public InvalidConfigReaderException(String str, Throwable th) {
		super(str, th);
	}

}
