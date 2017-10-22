package linus.io.config.configs;

import java.util.Random;

public class RandomCharConfig extends MultipleCharConfig{
	public RandomCharConfig() {}

	public RandomCharConfig(String name, char... values) {
		super(name, values);
	}

	public char getRandomValue(){
		return getPrimitiveValue()[new Random().nextInt(getPrimitiveValue().length)];
	}

	@Override
	public String toString() {
		return getName() + " " + SEPARATOR + " " + getRandomValue();
	}

}
