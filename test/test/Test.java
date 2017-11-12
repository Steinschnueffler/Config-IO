package test;

import linus.io.config.Config;

public class Test {

	private Test() {}

	public static void main(String[] args) throws Exception{
		Config<?> empty = Config.EMPTY_CONFIG;
		System.out.println(empty.toString());
	}
}
