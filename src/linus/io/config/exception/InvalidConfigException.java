package linus.io.config.exception;

import linus.io.config.Config;

/**
 * This {@link Exception} marks that a Config is invalid, not supported or
 * of the wrong type. A detail Exception can be get über Config.createException().
 *
 * @author Linus Dierheimer
 * @see Config
 * @since Java 1.4
 * @version 1.0
 *
 */
public class InvalidConfigException extends ConfigOperationException{
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new IvalidConfigException with the given String as Massage.
	 * @param str
	 */
	public InvalidConfigException(String str) {
		super(str);
	}

	public InvalidConfigException(String str, Throwable cause) {
		super(str, cause);
	}

}
