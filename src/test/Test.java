package test;

import linus.io.config.ComplexConfigWriter;
import linus.io.config.ConfigFile;
import linus.io.config.ThreadedConfigWriter;
import linus.io.config.configs.MultipleIntConfig;

public class Test {

	public static void main(String[] args) throws Exception {
		ConfigFile cf = new ConfigFile(".\\beispiel.cfg");
		cf.createNewFile();
		ComplexConfigWriter cw = cf.getComplexWriter();
		ThreadedConfigWriter tcw = new ThreadedConfigWriter(cw, true);
		tcw.writeAll(new MultipleIntConfig("Name", 1, 2, 3, 4, 5),
				new MultipleIntConfig("Name", 1, 2, 3, 4, 5),
				new MultipleIntConfig("Name", 1, 2, 3, 4, 5)
				);
		tcw.join();
		System.out.println(tcw.isWriting());
		tcw.close();
	}
}
