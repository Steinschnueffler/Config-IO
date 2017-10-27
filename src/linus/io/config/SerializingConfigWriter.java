package linus.io.config;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URI;

public class SerializingConfigWriter extends ConfigWriter{

	private Exception e = null;

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
		setConfigIOChars(chars);
	}

	@Override
	public void writeConfig(Config<?> cfg) {
		try {
			writer.println(getSerializedString(cfg.toSerializableConfig()));
		} catch (IOException e) {
			this.e = e;
		}
	}

	public Exception checkException(){
		Exception temp = e;
		e = null;
		return temp;
	}

	private String getSerializedString(Object obj) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		oos.flush();
		oos.close();
		baos.flush();
		baos.close();
		return new String(baos.toByteArray());
	}

}
