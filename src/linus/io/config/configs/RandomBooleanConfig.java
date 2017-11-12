package linus.io.config.configs;

import java.util.Random;

import linus.io.config.ConfigType;

public class RandomBooleanConfig extends MultipleBooleanConfig{
	public RandomBooleanConfig() {}

	public RandomBooleanConfig(String name, boolean... values) {
		super(name, values);
	}

	public boolean getRandomValue(){
		return getPrimitiveValue()[new Random().nextInt(getPrimitiveValue().length)];
	}

	@Override
	public ConfigType getConfigType() {
		return ConfigType.Random;
	}
	
	@Override
	public String toString() {
		return getName() + " " + SEPARATOR + " " + getRandomValue();
	}
}
