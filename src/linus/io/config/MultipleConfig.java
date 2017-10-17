package linus.io.config;

import java.util.Arrays;
import java.util.Iterator;

public class MultipleConfig extends Config<String[]> implements Iterable<String>{
	public MultipleConfig() {}

	public static final char SEPARATOR = '=';
	public static final char VALUE_START = '-';

	private String[] values;
	private String name;

	public MultipleConfig(String name, String... values){
		this.name = name;
		this.values = values;
	}

	@Override
	public String[] write() {
		String[] lines = new String[values.length + 1];
		lines[0] = name +" " +SEPARATOR;
		for(int i = 0; i < values.length; i++){
			lines[i + 1] = " " +VALUE_START +" " +values[i];
		}
		return lines;
	}

	@Override
	public void read(String[] lines) {
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		values = new String[lines.length - 1];
		for(int i = 1; i < lines.length; i++){
			values[i - 1] = lines[i].substring(lines[i].indexOf(VALUE_START) + 1).trim();
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String[] getValue() {
		return values;
	}

	@Override
	public Iterator<String> iterator() {
		return new Iterator<String>() {

			String[] vals = getValue();
			int zeiger = 0;

			@Override
			public boolean hasNext() {
				return zeiger < vals.length;
			}

			@Override
			public String next() {
				return vals[zeiger++];
			}
		};
	}

	@Override
	public String toString() {
		return getName() + " = " + Arrays.toString(getValue());
	}
}
