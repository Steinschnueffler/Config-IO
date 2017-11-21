package test;

import linus.io.config.SingleConfig;
import linus.io.config.configs.SingleStringConfig;

public class Test {

	private Test() {}

	public static void main(String[] args) throws Exception{
		SingleStringConfig ssc = (SingleStringConfig) SingleConfig.getSingleConfig("Name = Wert");
	}
}
