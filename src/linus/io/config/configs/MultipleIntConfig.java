package linus.io.config.configs;

import linus.io.config.Config;
import linus.io.config.MultipleConfig;
import linus.io.config.io.SerializableConfigData;

public class MultipleIntConfig extends MultipleConfig<Integer>{
	public MultipleIntConfig() {}

	private int[] primitive = new int[0];

	public MultipleIntConfig(String name, int... values){
		super(name);
		this.primitive = values;
		this.value = new Integer[values.length];
		for(int i = 0; i < values.length; i++){
			this.value[i] = Integer.valueOf(values[i]);
		}
	}

	public int[] getPrimitiveValue(){
		return primitive;
	}

	@Override
	public Config<Integer[]> read(String[] lines) {
		if(lines.length == 0){
			name = "";
			primitive = new int[0];
			value = new Integer[0];
			return this;
		}
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		primitive= new int[lines.length - 1];
		value = new Integer[lines.length - 1];
		for(int i = 1; i < lines.length; i++){
			primitive[i - 1] = Integer.parseInt(lines[i].substring(lines[i].indexOf(VALUE_START) + 1, lines[i].length()).trim());
			value[i - 1] = Integer.valueOf(primitive[i - 1]);
		}
		return this;
	}

	@Override
	public MultipleConfig<Integer> read(SerializableConfigData<Integer[]> data) {
		super.read(data);
		primitive = new int[value.length];
		for(int i = 0; i < primitive.length; i++){
			primitive[i] = value[i].intValue();
		}
		return this;
	}

}
