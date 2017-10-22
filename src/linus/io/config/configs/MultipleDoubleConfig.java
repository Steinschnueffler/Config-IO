package linus.io.config.configs;

import linus.io.config.Config;
import linus.io.config.MultipleConfig;

public class MultipleDoubleConfig extends MultipleConfig<Double>{
	public MultipleDoubleConfig() {}

	private double[] values = new double[0];
	private Double[] complex = new Double[0];

	public MultipleDoubleConfig(String name, double... values){
		super(name);
		this.values = values;
		for(int i = 0; i < complex.length; i++){
			complex[i] = Double.valueOf(values[i]);
		}
	}

	@Override
	public Double[] getValue() {
		return complex;
	}

	public double[] getPrimitiveValue(){
		return values;
	}

	@Override
	public Config<Double[]> read(String[] lines) {
		if(lines.length == 0){
			name = "";
			values = new double[0];
			complex = new Double[0];
			return this;
		}
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		values = new double[lines.length - 1];
		complex = new Double[lines.length - 1];
		for(int i = 1; i < lines.length; i++){
			values[i - 1] = Double.parseDouble(lines[i].substring(lines[i].indexOf(VALUE_START) + 1, lines[i].length()).trim());
			complex[i - 1] = Double.valueOf(values[i - 1]);
		}
		return this;
	}

	@Override
	protected void setValue(Double[] value) {
		this.complex = value;
		for(int i = 0; i < value.length; i++){
			this.values[i] = value[i].doubleValue();
		}
	}

}
