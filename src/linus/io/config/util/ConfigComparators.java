package linus.io.config.util;

import java.util.Arrays;
import java.util.Comparator;

import linus.io.config.Config;

public class ConfigComparators {

	public static final Comparator<Config<?>> COMMENTS_COMPARATOR = (c1, c2) -> {
		return Arrays.compare(c1.getComments(), c2.getComments());
	};

	public static final Comparator<Config<?>> NAME_COMPARATOR = (c1, c2) -> {
		return c1.getName().compareTo(c2.getName());
	};
	
	@SuppressWarnings("unchecked")
	public static final Comparator<Config<?>> VALUE_COMPARATOR = (c1, c2) -> {

		final Object o1 = c1.getValue();
		final Object o2 = c2.getValue();

		if (o1.equals(o2))
			return 0;

		try {
			return ((Comparable<Object>) o1).compareTo(o2);
		} catch (final Exception e) {
		}
		try {
			return ((Comparable<Object>) o2).compareTo(o1) * -1;
		} catch (final Exception e) {
		}

		final Class<?> cl1 = o1.getClass();
		final Class<?> cl2 = o2.getClass();

		if (!cl1.equals(cl2))
			return cl1.getSimpleName().compareTo(cl2.getSimpleName());

		return o1.toString().compareTo(o2.toString());
	};
	
	public static final Comparator<Config<?>> BASE_COMPARATOR = NAME_COMPARATOR.thenComparing(VALUE_COMPARATOR);
	
	public static final Comparator<Config<?>> ALL_COMPARATOR = BASE_COMPARATOR.thenComparing(COMMENTS_COMPARATOR);	
	
	public static final Comparator<Config<?>> REVERSED_COMPARATOR = ALL_COMPARATOR.reversed();

	private ConfigComparators() {};

}
