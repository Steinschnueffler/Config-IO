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
	protected Object source;
	
	protected Config<?> buffer = null;
	protected String lineBuffer = null;
	
	protected Exception lastException;
	
	public AbstractConfigReader(Reader in, Object source){
		this.reader = in instanceof BufferedReader ? (BufferedReader) in : new BufferedReader(in);
		
		this.source = source;
		try {
			buffer = firstConfig();
		} catch (ConfigReadException e) {
			lastException = e;
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
	
	@Override
	public Object getSource() {
		return source;
	}
	
	public ConfigHolder readAll() throws ConfigReadException {
		return ConfigHolder.loadFromReader(this);
	}

	@Override
	public void close() throws IOException {
		if(reader != null) reader.close();
	} 
}
