package linus.io.config;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URI;
import java.util.Scanner;

public class SerializingConfigReader implements ConfigReader{

	private Scanner reader;
	private InputStream source;
	private Config<?> buffer;

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
		reader = new Scanner(in);
		source = in;
		buffer = nextConfig();
	}

	@Override
	public void close() throws IOException {
		reader.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ConfigBase> E next() {
		Config<?> cfg = buffer;
		buffer = nextConfig();
		return (E) cfg;
	}

	@SuppressWarnings("unchecked")
	private Config<?> nextConfig(){
		SerializableConfigData<Object> data;
		try {
			data = (SerializableConfigData<Object>) nextData();
		} catch (IOException | ReflectiveOperationException e) {
			e.printStackTrace();
			return null;
		}catch(ReaderFinishedException e){
			return null;
		}
		try {
			Class<?> clazz = Class.forName(data.classPath);
			Config<Object> cfg = (Config<Object>) clazz.newInstance();
			return cfg.read(data);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
		return null;
	}

	private SerializableConfigData<?> nextData() throws IOException, ReflectiveOperationException{
		String next = reader.next();
		while(next.trim().length() == 0 || next.startsWith("#")){
			if(reader.hasNext())
				next = reader.next();
			else
				throw new ReaderFinishedException();
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(next.getBytes());
		ObjectInputStream ois = new ObjectInputStream(bais);
		SerializableConfigData<?> data = (SerializableConfigData<?>) ois.readObject();
		ois.close();
		bais.close();
		return data;
	}

	@Override
	public boolean hasNext() {
		return buffer != null;
	}

	@Override
	public InputStream getSource() {
		return source;
	}

}
