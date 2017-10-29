package linus.io.config;

import java.io.IOException;
import java.io.OutputStream;

import linus.io.config.exception.ConfigWriteEexception;
import linus.io.config.exception.InvalidConfigException;

public class ComplexConfigWriterBase extends ConfigWriter{

	public ComplexConfigWriterBase(OutputStream source) {
		super(source);
	}

	@Override
	public void writeConfig(Config<?> cfg) throws ConfigWriteEexception {

		if(cfg.getName().startsWith("" +chars.getClassEnd()))
			throw new InvalidConfigException("Name starts with the same character as class End: " + chars.getClassEnd());

		try {
			writer.write(chars.getClassStart() + cfg.getClass().getName());
			for(String s : cfg.write())
				writer.write(s);
			writer.write(chars.getClassEnd());
		}catch (IOException e) {
			throw new ConfigWriteEexception(e);
		}
	}

	@Override
	public Class<? extends ConfigReader> getFittingReader() {
		return ComplexConfigReader.class;
	}

}
