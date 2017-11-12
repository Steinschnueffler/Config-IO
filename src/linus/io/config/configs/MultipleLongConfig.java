package linus.io.config.configs;

import linus.io.config.MultipleConfig;
import linus.io.config.ValueContainable;
import linus.io.config.io.SerializableConfigData;

public class MultipleLongConfig extends MultipleConfig<Long>{
	public MultipleLongConfig() {}

	private long[] primitive = new long[0];

	public MultipleLongConfig(String name, long... values) {
		super(name);
		this.primitive = values;
		this.value = new Long[values.length];
		for(int i = 0; i < values.length; i++){
			this.value[i] = Long.valueOf(values[i]);
		}
	}

	public long[] getPrimitiveValue(){
		return primitive;
	}

	@Override
	public ValueContainable<Long[]> read(String[] lines) {
		if(lines.length == 0){
			name = "";
			primitive = new long[0];
			value = new Long[0];
			return this;
		}
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		primitive = new long[lines.length - 1];
		value = new Long[lines.length - 1];
		for(int i = 1; i < lines.length; i++){
			primitive[i - 1] = Long.parseLong(lines[i].substring(lines[i].indexOf(VALUE_START) + 1, lines[i].length()).trim());
			value[i - 1] = Long.valueOf(primitive[i - 1]);
		}
		return this;
	}

	@Override
	public MultipleConfig<Long> read(SerializableConfigData<Long[]> data) {
		super.read(data);
		primitive = new long[value.length];
		for(int i = 0; i < primitive.length; i++){
			primitive[i] = value[i].longValue();
		}
		return this;
	}

}
