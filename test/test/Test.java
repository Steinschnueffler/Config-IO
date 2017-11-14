package test;

import linus.io.config.configs.SingleStringConfig;
import linus.io.config.io.ConfigReader;
import linus.io.config.util.ConfigFile;
import linus.io.config.util.ConfigHolder;

public class Test {

	private Test() {}

	public static void main(String[] args) throws Exception{
		ConfigFile cf = new ConfigFile(".\\config.cfg");
		ConfigReader cr = cf.getFittingReader();
		System.out.println(cr.getClass());
		System.out.println(cr.hasNext());
		ConfigHolder ch = cr.readAll();
		SingleStringConfig ssc = (SingleStringConfig) ch.getConfig("Name");
		System.out.println(ssc.getValue());
	}
}
