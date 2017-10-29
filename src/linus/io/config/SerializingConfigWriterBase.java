package linus.io.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import linus.io.config.exception.ConfigWriteEexception;

public class SerializingConfigWriterBase extends ConfigWriter{

	public SerializingConfigWriterBase(OutputStream source) {
		super(source);
	}

	@Override
	public void writeConfig(Config<?> cfg) throws ConfigWriteEexception {
		try {
			writer.write(getSerializedString(cfg.toSerializableConfig()));
		} catch (IOException e) {
			throw new ConfigWriteEexception(e);
		}
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

	@Override
	public Class<SerializingConfigReader> getFittingReader() {
		return SerializingConfigReader.class;
	}

}
