package linus.io.config.io;

import java.io.BufferedReader;
import java.io.Reader;

import linus.io.config.Config;
import linus.io.config.exception.ConfigReadException;
import linus.io.config.util.ConfigHolder;

public abstract class ConfigReader {

	protected BufferedReader reader;
	protected Reader source;
	
	protected Config<?> buffer = null;
	protected String lineBuffer = null;
	
	public ConfigReader(Reader in) throws ConfigReadException {
		if(in.getClass().equals(BufferedReader.class))
			reader = (BufferedReader) in;
		else reader = new BufferedReader(in);
		
		this.source = in;
		buffer = readNext();
	}
	
	public Config<?> nextConfig() throws ConfigReadException{
		Config<?> cfg = buffer;
		buffer = readNext();
		return cfg;
	}
	
	protected abstract Config<?> readNext() throws ConfigReadException;

	public boolean hasNext() {
		return buffer != null;
	}
	
	public Reader getSource() {
		return source;
	}
	
	public ConfigHolder readAll() throws ConfigReadException {
		return ConfigHolder.loadFromReader(this);
	}
}
