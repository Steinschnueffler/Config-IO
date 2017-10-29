package linus.io.config;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import linus.io.config.exception.ConfigOperationException;

public class SerializingConfigReader extends SerializingConfigReaderBase{

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
		} catch (IOException e) {
			throw new ConfigOperationException(e);
		}
	}
	
	@Override
	public <E extends ConfigBase> E next(){
		try {
			return super.next();
		} catch (IOException | ReflectiveOperationException e) {
			throw new ConfigOperationException(e);
		}
	}
}
