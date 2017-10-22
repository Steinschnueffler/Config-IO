package linus.io.config.configs;

import java.util.Random;

public class RandomIntConfig extends MultipleIntConfig{
	public RandomIntConfig() {}

	public RandomIntConfig(String name, int... values) {
		super(name, values);
	}

	public int getRandomValue(){
		return getPrimitiveValue()[new Random().nextInt(getPrimitiveValue().length)];
	}

	@Override
	public String toString() {
		return getName() + " " + SEPARATOR + " " + getRandomValue();
	}

}
