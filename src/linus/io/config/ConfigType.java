package linus.io.config;

/**
 * This Enum contains the Info if a {@link Config} is Single, Multiple Custom or has a Value of null.
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
	 * This ConfigType marks that a Config can have different possible values,
	 * but only uses one of them.
	 */
	Random,

	/**
	 * This ConfigType mark that a Config is a custom Config and not
	 * an Instance of {@link SingleConfig} or {@link MultipleConfig}.
	 */
	Custom,

}
