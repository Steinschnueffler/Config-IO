package linus.io.config;

import java.util.Arrays;
import java.util.Iterator;

import linus.io.config.configs.MultipleStringConfig;

public abstract class MultipleConfig<E> extends Config<E[]> implements Iterable<E>{
	public MultipleConfig() {}

	public static final char VALUE_START = '-';

	protected E[] value = null;

	@SafeVarargs
	public MultipleConfig(String name, E... value){
		super(name);
		setValue(value);
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

	@Override
	public E[] getValue() {
		return value;
	}

	@Override
	protected void setValue(E[] value) {
		this.value = value;
	}

	@Override
	public MultipleStringConfig toStringConfig() {
		String[] vals = new String[value.length];
		for(int i = 0; i < vals.length; i++){
			vals[i] = getValue() == null ? null : getValue().toString();
		}
		return new MultipleStringConfig(getName(), vals);
	}

}
