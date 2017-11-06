package test;

import javafx.scene.paint.Paint;
import linus.io.config.Config;
import linus.io.config.configs.SingleStringConfig;
import linus.io.config.io.ComplexConfigReader;
import linus.io.config.io.ComplexConfigReaderBase;

public class Test {

	private Test() {}

	public static void main(String[] args) throws Exception{
		Config<Paint> ownConfig = new Config<Paint>("Name", Paint.valueOf("blue")) {
			
			
			@Override
			public String[] write() {
				return new String[] { getName() + " = " + getValueAsString()};
			}
			
			@Override
			public Config<Paint> read(String[] lines) {
				SingleStringConfig ssc = (SingleStringConfig) new SingleStringConfig().read(lines);
				this.name = ssc.getName();
				this.value = Paint.valueOf(ssc.getValue());
				return this;
			}
		};
		ComplexConfigReaderBase ccr = new ComplexConfigReader(".\\config.cfg");
		Config<?> cfg = ccr.next();
		System.out.println(cfg);
		ccr.close();
	}
}
