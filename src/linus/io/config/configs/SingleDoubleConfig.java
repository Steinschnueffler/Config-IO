package linus.io.config.configs;

import linus.io.config.Config;
import linus.io.config.SingleConfig;

public class SingleDoubleConfig extends SingleConfig<Double>{
	public SingleDoubleConfig() {
		setValue(0.0);
	}

	public SingleDoubleConfig(String name, double value) {
		super(name, value);
	}

	public double getPrimitiveValue(){
		return value == null ? 0.0 : value;
	}

	@Override
	public Config<Double> read(String[] lines) {
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		value = Double.parseDouble(lines[0].substring(lines[0].indexOf(SEPARATOR) + 1, lines[0].length()).trim());
		return this;
	}

	@Override
	protected void setValue(Double value) {
		this.value = value;
	}

}
