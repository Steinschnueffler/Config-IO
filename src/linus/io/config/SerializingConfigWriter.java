package linus.io.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

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
			writer.println(getSerializedString(cfg));
		} catch (IOException e) {
			this.e = e;
		}
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
