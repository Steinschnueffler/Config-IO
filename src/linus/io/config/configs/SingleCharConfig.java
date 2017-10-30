package linus.io.config.configs;

import linus.io.config.base.Config;
import linus.io.config.base.SingleConfig;

public class SingleCharConfig extends SingleConfig<Character>{
	public SingleCharConfig() {}

	public SingleCharConfig(String name, char value) {
		super(name, value);
	}

	public char getPrimitiveValue(){
		return value == null ? ' ' : value;
	}

	@Override
	public Config<Character> read(String[] lines) {
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		value = lines[0].substring(lines[0].indexOf(SEPARATOR) + 1, lines[0].length()).charAt(0);
		return this;
	}

}
