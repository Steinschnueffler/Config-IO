package linus.io.config.configs;

import java.util.Random;

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


}
