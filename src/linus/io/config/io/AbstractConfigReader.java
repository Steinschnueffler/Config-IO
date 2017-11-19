package linus.io.config.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import linus.io.config.Config;
import linus.io.config.ConfigBase;
import linus.io.config.exception.ConfigReadException;
import linus.io.config.util.ConfigHolder;

public abstract class AbstractConfigReader implements ConfigReader{

	protected BufferedReader reader;
	protected Reader source;
	
	protected Config<?> buffer = null;
	protected String lineBuffer = null;
	
	public AbstractConfigReader(Reader in){
		if(in.getClass().equals(BufferedReader.class))
			reader = (BufferedReader) in;
		else reader = new BufferedReader(in);
		
		this.source = in;
		try {
			buffer = firstConfig();
		} catch (ConfigReadException e) {
			buffer = null;
		}
	}
	
	protected abstract Config<?> firstConfig() throws ConfigReadException;
	
	@Override
	@SuppressWarnings("unchecked")
	public <E extends ConfigBase> E nextConfig() throws ConfigReadException{
		Config<?> cfg = buffer;
		buffer = readNext();
		return (E) cfg;
	}
	
	protected abstract Config<?> readNext() throws ConfigReadException;

	@Override
	public boolean hasNext() {
		return buffer != null;
	}
	
	public Reader getSource() {
		return source;
	}
	
	public ConfigHolder readAll() throws ConfigReadException {
		return ConfigHolder.loadFromReader(this);
	}

	@Override
	public void close() throws IOException {
		if(reader != null) reader.close();
		if(source != null) source.close();
	} 
}
