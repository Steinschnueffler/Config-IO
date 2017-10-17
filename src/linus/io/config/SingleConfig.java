package linus.io.config;

public class SingleConfig extends Config<String>{
	public SingleConfig() {}

	public static final char SEPARATOR = '=';

	private String name;
	private String value;

	public SingleConfig(String name, String value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String[] write() {
		String[] arr = {
			name+
			" "+
			SEPARATOR+
			" "+
			value
		};
		return arr;
	}

	@Override
	public void read(String[] lines) {
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		value = lines[0].substring(lines[0].indexOf(SEPARATOR) + 1, lines[0].length()).trim();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValue() {
		return value;
	}
}
