package test;

import java.math.BigInteger;

import linus.io.config.SingleConfig;
import linus.io.config.configs.SingleStringConfig;
import linus.io.config.util.ConfigHolder;

public class Test {

	private Test() {}

	public static void main(String[] args) throws Exception{
		System.out.println();
	}

	public static double durchschnitt(Number...numbers) {
		if(numbers.length == 0) return 0;
		double all = 0;
		for(Number n : numbers)
			all += n.doubleValue();
		return all / numbers.length;
	}
}
