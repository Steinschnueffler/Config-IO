package linus.io.config.configs;

import linus.io.config.Config;
import linus.io.config.SingleConfig;

public class SingleBooleanConfig extends SingleConfig<Boolean>{
	public SingleBooleanConfig() {}

	private boolean value;

	public SingleBooleanConfig(String name, boolean value) {
		super(name);
		this.value = value;
	}

	@Override
	public Boolean getValue() {
		return value;
	}

	public boolean getPrimitiveValue(){
		return value;
	}

	@Override
	public Config<Boolean> read(String[] lines) {
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		String val = lines[0].substring(lines[0].indexOf(SEPARATOR) + 1, lines[0].length());
		value = Boolean.parseBoolean(val);
		return this;
	}

	@Override
	protected void setValue(Boolean value) {
		this.value = value;
	}

}
