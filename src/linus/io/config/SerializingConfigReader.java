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

public class SerializingConfigReader extends ConfigReader{

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
	protected Config<?> nextConfig() {

		//zur nächsten Config
		if(!reader.hasNextLine()) return null;
		String line = reader.nextLine();
		while(line.startsWith("" +chars.getInfoStart())){
			if(!reader.hasNextLine()) return null;
			line = reader.nextLine();
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
		Config<Object> cfg = (Config<Object>) clazz.newInstance();
		return cfg.read(scd);
	}


}
