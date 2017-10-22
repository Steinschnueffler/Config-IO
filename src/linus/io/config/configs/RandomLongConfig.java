package linus.io.config.configs;

import java.util.Random;

public class RandomLongConfig extends MultipleLongConfig{
	public RandomLongConfig() {}

	public RandomLongConfig(String name, long... values) {
		super(name, values);
	}

	public long getRandomValue(){
		return getPrimitiveValue()[new Random().nextInt(getPrimitiveValue().length)];
	}

	@Override
	public String toString() {
		return getName() + " " + SEPARATOR + " " + getRandomValue();
	}

}
