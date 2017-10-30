package linus.io.config.configs;

import linus.io.config.base.Config;
import linus.io.config.base.MultipleConfig;
import linus.io.config.io.SerializableConfigData;

public class MultipleBooleanConfig extends MultipleConfig<Boolean>{
	public MultipleBooleanConfig() {}

	private boolean[] primitive = new boolean[0];

	public MultipleBooleanConfig(String name, boolean... values){
		super(name);
		this.primitive = values;
		this.value = new Boolean[values.length];
		for(int i = 0; i < values.length; i++){
			value[i] = Boolean.valueOf(values[i]);
		}
	}

	public boolean[] getPrimitiveValue(){
		return primitive;
	}

	@Override
	public Config<Boolean[]> read(String[] lines) {
		if(lines.length == 0){
			name = "";
			primitive = new boolean[0];
			value = new Boolean[0];
			return this;
		}
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		primitive = new boolean[lines.length - 1];
		value = new Boolean[lines.length - 1];
		for(int i = 1; i < lines.length; i++){
			primitive[i - 1] = Boolean.parseBoolean(lines[i].substring(lines[i].indexOf(VALUE_START) + 1, lines[i].length()).trim());
			value[i - 1] = Boolean.valueOf(primitive[i - 1]);
		}
		return this;
	}

	@Override
	public MultipleConfig<Boolean> read(SerializableConfigData<Boolean[]> data) {
		super.read(data);
		primitive = new boolean[value.length];
		for(int i = 0; i < value.length; i++){
			primitive[i] = value[i].booleanValue();
		}
		return this;
	}

}
