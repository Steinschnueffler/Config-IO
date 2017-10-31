package linus.io.config.configs;

import linus.io.config.Config;
import linus.io.config.MultipleConfig;
import linus.io.config.io.SerializableConfigData;

public class MultipleDoubleConfig extends MultipleConfig<Double>{
	public MultipleDoubleConfig() {}

	private double[] primitive = new double[0];

	public MultipleDoubleConfig(String name, double... values){
		super(name);
		this.primitive = values;
		this.value = new Double[values.length];
		for(int i = 0; i < values.length; i++){
			value[i] = Double.valueOf(values[i]);
		}
	}

	public double[] getPrimitiveValue(){
		return primitive;
	}

	@Override
	public Config<Double[]> read(String[] lines) {
		if(lines.length == 0){
			name = "";
			primitive = new double[0];
			value = new Double[0];
			return this;
		}
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		primitive = new double[lines.length - 1];
		value = new Double[lines.length - 1];
		for(int i = 1; i < lines.length; i++){
			primitive[i - 1] = Double.parseDouble(lines[i].substring(lines[i].indexOf(VALUE_START) + 1, lines[i].length()).trim());
			value[i - 1] = Double.valueOf(primitive[i - 1]);
		}
		return this;
	}

	@Override
	public MultipleConfig<Double> read(SerializableConfigData<Double[]> data) {
		super.read(data);
		primitive = new double[value.length];
		for(int i = 0; i < primitive.length; i++){
			primitive[i] = value[i].doubleValue();
		}
		return this;
	}

}
