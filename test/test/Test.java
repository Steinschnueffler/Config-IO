package test;

import linus.io.config.Config;

public class Test {

	private Test() {}

	public static void main(String[] args) throws Exception{
		Config<String> cfg = new Config<String>("Name", "Wert", true);
		Config<String> other = new Config<String>().unmodifiable();
		System.out.println(other.modifiable().setValues(cfg));
	}

}
