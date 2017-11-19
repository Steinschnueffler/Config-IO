package linus.io.config.io;

import java.io.Closeable;

import linus.io.config.ConfigBase;
import linus.io.config.exception.ConfigReadException;

public interface ConfigReader extends Closeable{

	public <E extends ConfigBase> E nextConfig() throws ConfigReadException;
	
	public boolean hasNext();
	
	public Object getSource();
}
