package linus.io.config.exception;

import linus.io.config.io.ConfigWriter;

/**
 * This {@link Exception} marks that a {@link ConfigWriter} is invalid
 * or unsuported. A detail Exception can be get over ConfigWriter.createException().
 *
 * @author Linus Dierheimer
 * @since java 1.5
 * @version 1.0
 * @see ConfigWriter
 *
 */
public class InvalidConfigWriterException extends ConfigOperationException{
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new IvalidConfigWriterException with the given String as Massage.
	 * @param str
	 */
	public InvalidConfigWriterException(String str) {
		super(str);
	}

}
