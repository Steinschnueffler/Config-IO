package linus.io.config;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;

import linus.io.config.configs.MultipleStringConfig;

public class SimpleConfigWriter implements ConfigWriter{

	private final PrintWriter writer;
	private OutputStream source;

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

	public void write(SingleConfig<?> sc){
		String str = sc.write()[0];
		if(str.startsWith("#")) throw new InvalidConfigException("Line starts with #");
		writer.println(str);
	}

	public void write(MultipleStringConfig mc){
		for(String s : mc.write()){
			if(s.startsWith("#")) throw new InvalidConfigException("Line starts with #");
			writer.println(s);
		}
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
	public void writeConfig(Config<?> cfg) {
		if(cfg instanceof SingleConfig)
			write((SingleConfig<?>) cfg);
		else if(cfg instanceof MultipleConfig)
			write((MultipleStringConfig) cfg);
	}

	@Override
	public OutputStream getSource() {
		return source;
	}
}
