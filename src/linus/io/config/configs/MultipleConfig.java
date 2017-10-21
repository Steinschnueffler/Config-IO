package linus.io.config.configs;


import java.util.Arrays;
import java.util.Iterator;

import linus.io.config.ConfigType;

public abstract class MultipleConfig<E> extends Config<E[]> implements Iterable<E>{
	public MultipleConfig() {}

	public static final char VALUE_START = '-';

	protected String name = "";

	public MultipleConfig(String name){
		this.name = name;
	}

	@Override
	public ConfigType getConfigType() {
		return ConfigType.Multiple;
	}

	@Override
	public String[] write() {
		String[] lines = new String[getValue().length + 1];
		lines[0] = name +" " +SEPARATOR;
		for(int i = 0; i < getValue().length; i++){
			lines[i + 1] = " " +VALUE_START +" " +getValue()[i];
		}
		return lines;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {

			private int zeiger = 0;
			private E[] vals = getValue();


			@Override
			public boolean hasNext() {
				return zeiger < vals.length;
			}

			@Override
			public E next() {
				return vals[zeiger++];
			}
		};
	}

	@Override
	public String toString() {
		return getName() + " " + SEPARATOR + " " + Arrays.deepToString(getValue());
	}

	@Override
	public MultipleConfig<E> clone() throws CloneNotSupportedException {
		return (MultipleConfig<E>) super.clone();
	}

}
