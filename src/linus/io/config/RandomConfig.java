package linus.io.config;

import java.util.Random;

public class RandomConfig extends MultipleConfig{
	public RandomConfig() {}

	public RandomConfig(String name, String... values){
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
}
