package linus.io.config.configs;

import java.util.Random;

import linus.io.config.ConfigType;

public class RandomStringConfig extends MultipleStringConfig{
	public RandomStringConfig() {}

	public RandomStringConfig(String name, String... values){
		super(name, values);
	}

	public String getRandomValue(){
		String[] values = getValue();
		return values[new Random().nextInt(values.length)];
	}

	@Override
	public String toString() {
		return getName() + " = " +getRandomValue();
	}

	@Override
	public ConfigType getConfigType() {
		return ConfigType.Random;
	}
}
