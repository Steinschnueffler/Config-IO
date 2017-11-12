package linus.io.config.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import linus.io.config.Config;
import linus.io.config.ValueContainable;
import linus.io.config.exception.ConfigReadException;

public class SerializingConfigReaderBase extends ConfigReader{

	public SerializingConfigReaderBase(InputStream source) {
		super(source);
	}

	@Override
	protected ValueContainable<?> nextConfig() throws ConfigReadException {

		String line;
		
		//zur nächsten Config
		try {
			if(reader.ready()) return null;
			line = reader.readLine();
			while(line.startsWith("" +chars.getInfoStart())){
				if(reader.ready()) return null;
				line = reader.readLine();
			}
		}catch(IOException e) {
			throw new ConfigReadException(e);
		}

		try {
			return getConfig(line);
		} catch (IOException e) {
			return null;
		} catch (ReflectiveOperationException e) {
			return nextConfig();
		}

	}

	@SuppressWarnings("unchecked")
	private ValueContainable<?> getConfig(String str) throws IOException, ReflectiveOperationException{
		ByteArrayInputStream bais = new ByteArrayInputStream(str.getBytes());
		ObjectInputStream ois = new ObjectInputStream(bais);
		SerializableConfigData<Object> scd = (SerializableConfigData<Object>) ois.readObject();
		ois.close();
		bais.close();

		Class<?> clazz = Class.forName(scd.classPath);
		Config<Object> cfg = (Config<Object>) clazz.getConstructor().newInstance();
		return cfg.read(scd);
	}

}
