package test;

import linus.io.config.SimpleConfigWriter;
import linus.io.config.ThreadedConfigWriter;

public class Test {

	public static void main(String[] args) throws Exception {
		ThreadedConfigWriter tcw = new ThreadedConfigWriter(new SimpleConfigWriter(""));
		tcw.close();
	}
}
