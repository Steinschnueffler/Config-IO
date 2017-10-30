package linus.io.config.base;

/**
 * This Enum contains the Info of a {@link Config} is Single or Multiple.
 *
 * @author Linus Dierheimer
 * @since java 1.5
 * @version 1.0
 *
 */
public enum ConfigType {

	/**
	 * This ConfigType marks that a Config has only one Value / return type.
	 */
	Single,

	/**
	 * This ConfigType marks that a Config can have a lot of different
	 * Values / return Types
	 */
	Multiple,

	/**
	 * This ConfigType mark that a Config is a custom Config and not
	 * an Instance of {@link SingleConfig} or {@link MultipleConfig}.
	 */
	Custom,

	/**
	 * This ConfigType marks that a Config doesn't has any value.
	 */
	NULL

}
