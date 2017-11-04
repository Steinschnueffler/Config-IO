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
		if(o1.equals(o2)) return 0;
		
		int type = o1.getConfigType().compareTo(o2.getConfigType());
		if(type != 0) return type;
		
		int clazz = compareClass(o1.getClass(), o2.getClass());
		if(clazz != 0) return clazz;
		
		int valueClazz = compareClass(o1.getValue().getClass(), o2.getValue().getClass());
		if(valueClazz != 0) return valueClazz;
		
		int value = compareObject(o1.getValue(), o2.getValue());
		if(value != 0) return value;
		
		int name = compareString(o1.getName(), o2.getName());
		if(name != 0) return name;
		
		return compareString(o1.toString(), o2.toString());
	}
	
	private static int compareString(String s1, String s2) {
		int length1 = s1.length();
		int length2 = s2.length();
		
		int max = length1;
		if(length2 < max) max = length2;
		
		for(int i = 0; i < max; i++) {
			Character first = s1.charAt(i);
			int comp = first.compareTo(s2.charAt(i));
			if(comp != 0) return comp;
		}
		
		if(length1 < length2) return -1;
		if(length1 > length2) return 1;
		return 0;
			
	}
	
	private static int compareClass(Class<?> c1, Class<?> c2) {
		if(c1.equals(c2)) return 0;
		int pack = compareString(c1.getPackageName(), c2.getPackageName());
		if(pack != 0) return pack;
		return compareString(c1.getSimpleName(), c2.getSimpleName());
	}
	
	@SuppressWarnings("unchecked")
	private static int compareObject(Object o1, Object o2) {
		try {
			return ((Comparable<Object>) o1).compareTo(o2);
		}catch(Exception e) {}
		try {
			return ((Comparable<Object>) o2).compareTo(o1) * -1;
		}catch(Exception e) {}
		return 0;
	}

}
