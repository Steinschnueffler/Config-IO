package linus.io.config.util;

import java.util.Comparator;

import linus.io.config.Config;

/**
 * This implementation of {@link Comparator} is able to compare
 * {@link Config}s in a numeric Order, after Type, name and value.
 * It can be used to sort an Array or a Collection of Configs and Filter them.
 *
 * @author Linus Dierheimer
 * @since java 1.4
 * @version 1.0
 *
 */
public class ConfigComparator implements Comparator<Config<?>>{

	@Override
	public int compare(Config<?> o1, Config<?> o2) {
		return compareStatic(o1, o2);
	}

	/**
	 * a static implementation of {@link #compare(Config, Config)}.
	 *
	 * @param o1 the first Config
	 * @param o2 the second Config
	 * @return the result: -1 if lower, 0 if equal and 1 if higer
	 */
	public static int compareStatic(Config<?> o1, Config<?> o2){
		return o1.compareTo(o2);
	}

}
