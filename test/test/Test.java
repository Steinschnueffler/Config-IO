package test;

import linus.io.config.util.ConfigHolder;

public class Test {

	private Test() {}

	public static void main(String[] args) throws Exception{
		ConfigHolder ch = ConfigHolder.loadFromeFile(".\\config.cfg");
		System.out.println(ch);
	}
}
