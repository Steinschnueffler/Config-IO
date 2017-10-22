package linus.io.config;

public abstract class SingleConfig<E> extends Config<E>{
	public SingleConfig() {}

	protected E value = null;

	public SingleConfig(String name) {
		super(name);
	}

	public SingleConfig(String name, E value){
		super(name);
		setValue(value);
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

	@Override
	public E getValue() {
		return value;
	}

	@Override
	protected void setValue(E value) {
		this.value = value;
	}
}
