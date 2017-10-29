package linus.io.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class SerializingConfigReaderBase extends ConfigReader{

	public SerializingConfigReaderBase(InputStream source) {
		super(source);
	}

	@Override
	protected Config<?> nextConfig() throws IOException {

		//zur nächsten Config
		if(reader.ready()) return null;
		String line = reader.readLine();
		while(line.startsWith("" +chars.getInfoStart())){
			if(reader.ready()) return null;
			line = reader.readLine();
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
	private Config<?> getConfig(String str) throws IOException, ReflectiveOperationException{
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
