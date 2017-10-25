package linus.io.config;

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
public class InvalidConfigException extends IllegalArgumentException{
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new IvalidConfigException with the given String as Massage.
	 * @param str
	 */
	public InvalidConfigException(String str) {
		super(str);
	}

	InvalidConfigException(Config<?> cfg, Throwable cause) {
		super(cfg.toString(), cause);
	}

}
