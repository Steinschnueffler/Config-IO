package linus.io.config.configs;

import linus.io.config.ConfigType;

public abstract class SingleConfig<E> extends Config<E>{
	public SingleConfig() {}

	public SingleConfig(String name) {
		super(name);
	}

	@Override
	public ConfigType getConfigType() {
		return ConfigType.Single;
	}

	@Override
	public String[] write() {
		String[] arr = {
				name+
				" "+
				SEPARATOR+
				" "+
				getValue().toString()
			};
			return arr;
	}

	@Override
	public SingleConfig<E> clone() throws CloneNotSupportedException {
		return (SingleConfig<E>) super.clone();
	}
}
