package linus.io.config.configs;

import java.util.Comparator;

public class ConfigComparator implements Comparator<Config<?>>{

	@Override
	public int compare(Config<?> o1, Config<?> o2) {
		return compareStatic(o1, o2);
	}

	public static int compareStatic(Config<?> o1, Config<?> o2){
		return o1.compareTo(o2);
	}

}
