package linus.io.config.configs;

import linus.io.config.base.Config;
import linus.io.config.base.MultipleConfig;

public class MultipleStringConfig extends MultipleConfig<String>{
	public MultipleStringConfig() {}

	public MultipleStringConfig(String name, String... values){
		super(name, values);
	}

	@Override
	public Config<String[]> read(String[] lines) {
		if(lines.length == 0){
			name = "";
			value = new String[0];
			return this;
		}
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		value = new String[lines.length - 1];
		for(int i = 1; i < lines.length; i++){
			value[i - 1] = lines[i].substring(lines[i].indexOf(VALUE_START) + 1, lines[i].length()).trim();
		}
		return this;
	}

	@Override
	public MultipleStringConfig toStringConfig() {
		try {
			return (MultipleStringConfig) clone();
		} catch (CloneNotSupportedException e) {
			return new MultipleStringConfig(getName(), getValue());
		}
	}

}
