package linus.io.config.configs;

public class SingleLongConfig extends SingleConfig<Long>{
	public SingleLongConfig() {}

	private long value = 0l;

	public SingleLongConfig(String name, long value) {
		super(name);
		this.value = value;
	}

	@Override
	public Long getValue() {
		return value;
	}

	public long getPrimitiveValue(){
		return value;
	}

	@Override
	public Config<Long> read(String[] lines) {
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		value = Long.parseLong(lines[0].substring(lines[0].indexOf(SEPARATOR) + 1, lines[0].length()).trim());
		return this;
	}

}
