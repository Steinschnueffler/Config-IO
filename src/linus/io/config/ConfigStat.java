package linus.io.config;

public enum ConfigStat {

	/**
	 * This ConfigStat marks that a Config has a name AND a value.
	 */
	Normal,
	
	/**
	 * This ConfigStat marks that a Config has a name, AND it doesn't has a value.
	 */
	Name,
	
	/**
	 * This ConfigStat marks that a Config has a value, AND it doesn't has a name. 
	 */
	Value,
	
	/**
	 * This ConfigType marks that a Config doesn't has a Name AND doesn't has a value.
	 */
	Empty
	
}
