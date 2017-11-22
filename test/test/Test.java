package test;

import linus.io.config.configs.SingleBooleanConfig;

public class Test {

	private Test() {}

	public static void main(String[] args) throws Exception{
		SingleBooleanConfig sbc = new SingleBooleanConfig("name", true);
		System.out.println(sbc.isPrimitive());
	}

}
