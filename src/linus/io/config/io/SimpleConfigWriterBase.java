package linus.io.config.io;

import java.io.Writer;

import linus.io.config.Config;
import linus.io.config.MultipleConfig;
import linus.io.config.SingleConfig;
import linus.io.config.exception.ConfigWriteEexception;

public class SimpleConfigWriterBase extends AbstractConfigWriter{

	public SimpleConfigWriterBase(Writer writer, Object source) {
		super(writer, source);
	}

	
	@Override
	public void writeConfig(Config<?> cfg) throws ConfigWriteEexception {
		if(cfg instanceof SingleConfig)
			writeConfig((SingleConfig<?>) cfg);
		else if(cfg instanceof MultipleConfig)
			writeConfig((MultipleConfig<?>) cfg);
		else throw cfg.createException("Only can write Single and Multiple Configs");
	}
	
	public void writeConfig(SingleConfig<?> sc) throws ConfigWriteEexception {
		writeln(sc.writeSimple());
	}
	
	public void writeConfig(MultipleConfig<?> mc) throws ConfigWriteEexception {
		for(String s : mc.writeSimple())
			writeln(s);
	}

	@Override
	public Class<? extends ConfigReader> getFittingReader() {
		return SimpleConfigReader.class;
	}

}
