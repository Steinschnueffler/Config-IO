package linus.io.config.configs;

import java.util.Random;

import linus.io.config.ConfigType;

public class RandomDoubleConfig extends MultipleDoubleConfig{
	public RandomDoubleConfig() {}

	public RandomDoubleConfig(String name, double... values) {
		super(name, values);
	}

	public double getRandomValue(){
		return getPrimitiveValue()[new Random().nextInt(getPrimitiveValue().length)];
	}

	@Override
	public String toString() {
		return getName() + " " + SEPARATOR + " " + getRandomValue();
	}

	@Override
	public ConfigType getConfigType() {
		return ConfigType.Random;
	}
}
