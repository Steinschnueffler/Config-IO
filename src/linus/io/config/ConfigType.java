package linus.io.config;

import linus.io.config.configs.Config;

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
	Multiple

}
