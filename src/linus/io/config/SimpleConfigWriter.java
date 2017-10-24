package linus.io.config;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;

public class SimpleConfigWriter implements ConfigWriter{

	private final PrintWriter writer;
	private final OutputStream source;
	private ConfigIOChars chars;

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
		writer = new PrintWriter(out);
		source = out;
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
	public void close(){
		writer.flush();
		writer.close();
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
	public OutputStream getSource() {
		return source;
	}

	@Override
	public void writeHeader() {
		for(String s : chars.getHeader())
			writeInfo(s);
	}

	@Override
	public ConfigIOChars getConfigIOChars() {
		return chars;
	}

	@Override
	public void setConfigIOChars(ConfigIOChars chars) {
		this.chars = chars;
		writer.println("%ConfigIOChars : " +chars.getClass().getName());
	}
}
