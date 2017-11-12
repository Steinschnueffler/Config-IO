package linus.io.config.configs;

import linus.io.config.SingleConfig;
import linus.io.config.io.SerializableConfigData;

public class SingleStringConfig extends SingleConfig<String>{
	public SingleStringConfig() {}

	public SingleStringConfig(String name, String value) {
		super(name);
		this.value = value;
	}

	@Override
	public SingleStringConfig read(String[] lines) {
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		value = lines[0].substring(lines[0].indexOf(SEPARATOR) + 1, lines[0].length()).trim();
		return this;
	}
	
	@Override
	public SingleStringConfig read(SerializableConfigData<String> data) {
		super.read(data);
		return this;
	}

	@Override
	public SingleStringConfig toStringConfig() {
		try {
			return (SingleStringConfig) clone();
		} catch (CloneNotSupportedException e) {
			return new SingleStringConfig(getName(), getValue());
		}
	}
}
