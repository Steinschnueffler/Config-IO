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
		if(!(cfg instanceof SingleConfig || cfg instanceof MultipleConfig))
			throw new InvalidConfigException("Config isn't a subclass of SingleConfig or MultipleConfig");
		for(String s : cfg.write())
			writer.println(s);
	}

	@Override
	public OutputStream getSource() {
		return source;
	}
}
