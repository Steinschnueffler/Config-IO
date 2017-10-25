package linus.io.config;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;

public class SimpleConfigWriter extends ConfigWriter{

	private final PrintWriter writer;

	public SimpleConfigWriter(String pathName) throws FileNotFoundException {
		this(new File(pathName));
	}

	public SimpleConfigWriter(URI uri) throws FileNotFoundException {
		this(new File(uri));
	}

	public SimpleConfigWriter(FileDescriptor fd) {
		this(new FileOutputStream(fd));
	}

	public SimpleConfigWriter(File f) throws FileNotFoundException {
		this(new FileOutputStream(f));
	}

	public SimpleConfigWriter(OutputStream out) {
		super(out);
		writer = new PrintWriter(out);
	}

	public SimpleConfigWriter(OutputStream out, ConfigIOChars chars) {
		this(out);
		setConfigIOChars(chars);
	}

	@Override
	public void writeInfo(String str){
		writer.println("#" +str);
	}

	@Override
	public void writeln(){
		writer.println();
	}

	@Override
	public void close() throws IOException{
		writer.flush();
		writer.close();
		if(source != null) source.close();
	}

	@Override
	@Deprecated
	public void writeConfig(Config<?> cfg) {
		if(cfg instanceof SingleConfig<?>)
			writeConfig((SingleConfig<?>) cfg);
		else if(cfg instanceof MultipleConfig<?>)
			writeConfig((MultipleConfig<?>) cfg);
		else throw new InvalidConfigException("Config must be a super Type of MultipleConfig<?> or SingleConfig<?>");
	}

	public void writeConfig(SingleConfig<?> sc){
		writer.println(sc.writeSimple());
	}

	public void writeConfig(MultipleConfig<?> mc){
		for(String s : mc.writeSimple())
			writer.println(s);
	}

	@Override
	public void writeHeader() {
		for(String s : chars.getHeader())
			writeInfo(s);
	}

	@Override
	public void setConfigIOChars(ConfigIOChars chars) {
		super.setConfigIOChars(chars);
		writer.println("%ConfigIOChars : " +chars.getClass().getName());
	}
}
