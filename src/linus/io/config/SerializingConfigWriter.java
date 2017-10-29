package linus.io.config;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import linus.io.config.exception.ConfigOperationException;

public class SerializingConfigWriter extends SerializingConfigWriterBase{

	public SerializingConfigWriter(String pathName) throws FileNotFoundException {
		this(new File(pathName));
	}

	public SerializingConfigWriter(URI uri) throws FileNotFoundException {
		this(new File(uri));
	}

	public SerializingConfigWriter(FileDescriptor fd) {
		this(new FileOutputStream(fd));
	}

	public SerializingConfigWriter(File f) throws FileNotFoundException {
		this(new FileOutputStream(f));
	}

	public SerializingConfigWriter(OutputStream os) {
		super(os);
	}

	public SerializingConfigWriter(OutputStream os, ConfigIOChars chars) {
		this(os);
		try {
			setConfigIOChars(chars);
		} catch (IOException e) {
			throw new ConfigOperationException(e);
		}
	}

}
