package linus.io.config.configs;

import linus.io.config.Config;
import linus.io.config.MultipleConfig;

public class MultipleCharConfig extends MultipleConfig<Character>{
	public MultipleCharConfig() {}

	private char[] primitive = new char[0];

	public MultipleCharConfig(String name, char... values) {
		super(name);
		this.primitive= values;
		for(int i = 0; i < values.length; i++){
			this.value[i] = Character.valueOf(values[i]);
		}
	}

	public char[] getPrimitiveValue(){
		return primitive;
	}

	@Override
	public Config<Character[]> read(String[] lines) {
		if(lines.length == 0){
			name = "";
			primitive = new char[0];
			value = new Character[0];
			return this;
		}
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		primitive = new char[lines.length - 1];
		value = new Character[lines.length - 1];
		for(int i = 1; i < lines.length; i++){
			primitive[i - 1] = lines[i].substring(lines[i].indexOf(VALUE_START) + 1, lines[i].length()).trim().charAt(0);
			value[i - 1] = Character.valueOf(primitive[i - 1]);
		}
		return this;
	}

	@Override
	protected void setValue(Character[] value) {
		super.setValue(value);
		for(int i = 0; i < value.length; i++){
			this.value[i] = value[i].charValue();
		}
	}

}
