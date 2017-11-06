package linus.io.config.io;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import linus.io.config.ConfigBase;
import linus.io.config.exception.ConfigReadException;

public class ComplexConfigReader extends ComplexConfigReaderBase{

	private Exception e = null;
	
	public Exception checkException() {
		return e;
	}
	
	public void supressException() {
		e = null;
	}
	
	public ComplexConfigReader(File f) throws FileNotFoundException {
		this(new FileInputStream(f));
	}

	public ComplexConfigReader(InputStream source){
		super(source);
	}
	
	public ComplexConfigReader(String pathName) throws FileNotFoundException {
		this(new File(pathName));
	}
	
	public ComplexConfigReader(FileDescriptor fd) {
		this(new FileInputStream(fd));
	}
	
	@Override
	public void close(){
		try {
			super.close();
			e = null;
		} catch (ConfigReadException e) {
			this.e = e;
		}
	}
	
	@Override
	public <E extends ConfigBase> E next() {
		try {
			E temp = super.next();
			e = null;
			return temp;
		} catch (ConfigReadException | ReflectiveOperationException e) {
			this.e = e;
			return null;
		}
	}
	
}