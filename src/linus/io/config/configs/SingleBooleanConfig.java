package linus.io.config.configs;

import linus.io.config.SingleConfig;

public class SingleBooleanConfig extends SingleConfig<Boolean>{
	public SingleBooleanConfig() {}

	public SingleBooleanConfig(String name, boolean value) {
		super(name, value);
	}

	public boolean getPrimitiveValue(){
		return value == null ? false : value;
	}

	@Override
	public SingleBooleanConfig read(String[] lines) {
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		String val = lines[0].substring(lines[0].indexOf(SEPARATOR) + 1, lines[0].length());
		value = Boolean.parseBoolean(val);
		return this;
	}
}
