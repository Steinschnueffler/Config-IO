package linus.io.config.io;

import java.io.IOException;
import java.io.Writer;

import linus.io.config.Config;
import linus.io.config.exception.ConfigWriteEexception;

public abstract class AbstractConfigWriter implements ConfigWriter {

	private Writer writer;
	
	public AbstractConfigWriter(Writer writer) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void writeConfig(Config<?> cfg) throws ConfigWriteEexception {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeInfo(String info) throws ConfigWriteEexception {
		// TODO Auto-generated method stub

	}

	@Override
	public void flush() throws IOException {
		if(writer != null) writer.flush();
	}
	
	@Override
	public void close() throws IOException {
		flush();
		if(writer != null) writer.close();
	}
	
	
}
