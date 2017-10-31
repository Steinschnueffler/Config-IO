package linus.io.config.io;

import java.io.IOException;
import java.io.OutputStream;

import linus.io.config.Config;
import linus.io.config.MultipleConfig;
import linus.io.config.SingleConfig;
import linus.io.config.exception.ConfigWriteEexception;

public class SimpleConfigWriterBase extends ConfigWriter{

	public SimpleConfigWriterBase(OutputStream source, ConfigIOChars chars) {
		super(source, chars);
	}
	
	public SimpleConfigWriterBase(OutputStream out) {
		super(out);
	}

	@Override
	@Deprecated
	public void writeConfig(Config<?> cfg) throws ConfigWriteEexception {
		if(cfg instanceof SingleConfig<?>)
			writeConfig((SingleConfig<?>) cfg);
		else if(cfg instanceof MultipleConfig<?>)
			writeConfig((MultipleConfig<?>) cfg);
		else
			throw cfg.createException("");
	}

	public void writeConfig(SingleConfig<?> sc) throws ConfigWriteEexception{
		try {
		writeln(sc.writeSimple());
		} catch (IOException e) {
			throw new ConfigWriteEexception(e);
		}
	}

	public void writeConfig(MultipleConfig<?> mc) throws ConfigWriteEexception{
		for(String s : mc.writeSimple())
			try {
				writeln(s);
			} catch (IOException e) {
				throw new ConfigWriteEexception(e);
			}
	}

	@Override
	public Class<SimpleConfigReader> getFittingReader() {
		return SimpleConfigReader.class;
	}

}
