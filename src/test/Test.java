package test;

import linus.io.config.configs.MultipleStringConfig;
import linus.io.config.io.SimpleConfigWriter;
import linus.io.config.util.ConfigFile;

public class Test {

	public static void main(String[] args) throws Exception {
		ConfigFile cf = new ConfigFile(".\\config.cfg");
		SimpleConfigWriter scw = cf.getSimpleWriter();
		scw.writeConfig(new MultipleStringConfig("Name", "1", "1", "2", "2", "2", "3", "3", "3", "3"));
		scw.close();
	}
}
