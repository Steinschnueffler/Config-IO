package test;

import linus.io.config.configs.SingleStringConfig;

public class Test {

	public static void main(String[] args) throws Exception {
		throw new SingleStringConfig("Name", "Wert").createException();
	}
}
