package test;

import java.io.IOException;

import linus.io.config.Config;
import linus.io.config.SingleConfig;
import linus.io.config.configs.SingleStringConfig;

public class Test {

	private Test() {}

	public static void main(String[] args){
		SingleConfig<?> ssc = new SingleStringConfig();
		System.out.println(ssc.hasName() +" : " +ssc.getName());
		System.out.println(ssc.containsValue() +" : " +ssc.getValue());
		System.out.println();
		Config<?> cfg = ssc.normalize();
		try {
			cfg.writeTo(System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
