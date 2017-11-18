package linus.io.config.io;

import java.io.Reader;

import linus.io.config.Config;
import linus.io.config.exception.ConfigReadException;

public class SimpleConfigReaderBase extends ConfigReader{

	public SimpleConfigReaderBase(Reader in) throws ConfigReadException {
		super(in);
	}
	

	@Override
	protected Config<?> readNext() throws ConfigReadException {
		lineBuffer = reader.readLine();
	}

}
