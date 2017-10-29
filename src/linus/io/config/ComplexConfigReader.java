package linus.io.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import linus.io.config.exception.ConfigOperationException;

public class ComplexConfigReader extends ComplexConfigReaderBase{

	public ComplexConfigReader(File f) throws FileNotFoundException {
		this(new FileInputStream(f));
	}

	public ComplexConfigReader(InputStream source){
		super(source);
	}
	
	@Override
	public void close(){
		try {
			super.close();
		} catch (IOException e) {
			throw new ConfigOperationException(e);
		}
	}
	
	@Override
	public <E extends ConfigBase> E next() {
		try {
			return super.next();
		}catch(Exception e) {
			throw new ConfigOperationException(e);
		}
	}
	
}