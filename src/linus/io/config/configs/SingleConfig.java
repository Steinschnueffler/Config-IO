package linus.io.config.configs;

import linus.io.config.ConfigType;

public abstract class SingleConfig<E> extends Config<E>{

	protected String name = "";

	public SingleConfig() {}

	public SingleConfig(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
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
