package linus.io.config.io;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;

import linus.io.config.ConfigBase;
import linus.io.config.exception.ConfigReadException;

public class SerializingConfigReader extends SerializingConfigReaderBase{

	private Exception e = null;
	
	public Exception checkException() {
		return e;
	}
	
	public void supressException() {
		e = null;
	}
	
	public SerializingConfigReader(String pathName) throws FileNotFoundException {
		this(new File(pathName));
	}

	public SerializingConfigReader(FileDescriptor fd) {
		this(new FileInputStream(fd));
	}

	public SerializingConfigReader(URI uri) throws FileNotFoundException {
		this(new File(uri));
	}

	public SerializingConfigReader(File f) throws FileNotFoundException {
		this(new FileInputStream(f));
	}

	public SerializingConfigReader(InputStream in) {
		super(in);
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
		} catch (ConfigReadException e) {
			this.e = e;
			return null;
		}
	}
}
