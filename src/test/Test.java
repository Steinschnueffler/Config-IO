package test;

import linus.io.config.ConfigFile;
import linus.io.config.ConfigReader;

public class Test {

	public static void main(String[] args) throws Exception {
		ConfigFile cf = new ConfigFile("");
		ConfigReader cr = cf.getFittingReader();
		System.out.println(cr.next());
		cr.close();
	}
}
