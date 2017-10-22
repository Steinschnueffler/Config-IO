package linus.io.config.configs;

import linus.io.config.Config;
import linus.io.config.SingleConfig;

public class SingleStringConfig extends SingleConfig<String>{
	public SingleStringConfig() {}

	private String value;

	public SingleStringConfig(String name, String value) {
		super(name);
		this.value = value;
	}

	@Override
	public Config<String> read(String[] lines) {
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		value = lines[0].substring(lines[0].indexOf(SEPARATOR) + 1, lines[0].length()).trim();
		return this;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	protected void setValue(String value) {
		this.value = value;
	}
}
