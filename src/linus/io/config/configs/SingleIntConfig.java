package linus.io.config.configs;

import linus.io.config.PrimitiveConfig;
import linus.io.config.SingleConfig;

@PrimitiveConfig("int")
public class SingleIntConfig extends SingleConfig<Integer>{
	public SingleIntConfig() {}

	public SingleIntConfig(String name, int value) {
		super(name, value);
	}

	@Override
	public SingleIntConfig read(String[] lines) {
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		value = Integer.parseInt(lines[0].substring(lines[0].indexOf(SEPARATOR) + 1, lines[0].length()).trim());
		return this;
	}
	
	public int getPrimitiveValue(){
		return value == null ? 0 : value;
	}

}
