package linus.io.config.configs;

import java.util.Random;

public class RandomBooleanConfig extends MultipleBooleanConfig{
	public RandomBooleanConfig() {}

	public RandomBooleanConfig(String name, boolean... values) {
		super(name, values);
	}

	public boolean getRandomValue(){
		return getPrimitiveValue()[new Random().nextInt(getPrimitiveValue().length)];
	}

	@Override
	public String toString() {
		return getName() + " " + SEPARATOR + " " + getRandomValue();
	}
}
