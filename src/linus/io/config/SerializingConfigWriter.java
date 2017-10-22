package linus.io.config;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import linus.io.config.configs.Config;
import linus.io.config.configs.SerializableConfigData;

public class SerializingConfigWriter implements ConfigWriter{

	private PrintStream writer;
	private OutputStream source;

	private Exception e = null;

	public SerializingConfigWriter(OutputStream os) {
		source = os;
		writer = new PrintStream(os);
	}

	@Override
	public void close() throws IOException {
		writer.flush();
		writer.close();
	}

	@Override
	public void writeInfo(String info) {
		writer.println("#" +info);
	}

	@Override
	public void writeConfig(Config<?> cfg) {
		try {
			writeSerializable(cfg.toSerializableConfig());
		} catch (IOException e) {
			this.e = e;
		}
	}

	private void writeSerializable(SerializableConfigData<?> data) throws IOException{
		ObjectOutputStream oos = new ObjectOutputStream(writer);
		oos.writeObject(data);
		oos.flush();
		oos.close();
		writeln();
	}

	@Override
	public void writeln() {
		writer.println();
	}

	@Override
	public OutputStream getSource() {
		return source;
	}

	public Exception checkException(){
		Exception temp = e;
		e = null;
		return temp;
	}

}
