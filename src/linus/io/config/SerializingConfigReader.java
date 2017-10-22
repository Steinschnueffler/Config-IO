package linus.io.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Scanner;

import linus.io.config.configs.Config;
import linus.io.config.configs.ConfigBase;
import linus.io.config.configs.SerializableConfigData;

public class SerializingConfigReader implements ConfigReader{

	private Scanner reader;
	private InputStream source;
	private Config<?> buffer;

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

	private Config<?> nextConfig(){
		SerializableConfigData<?> data;
		try {
			data = nextData();
		} catch (IOException | ReflectiveOperationException e) {
			e.printStackTrace();
			return null;
		}catch(ReaderFinishedException e){
			return null;
		}
		try {
			return ReflectiveConfigLoader.loadFromData(data);
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
