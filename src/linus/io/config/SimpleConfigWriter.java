package linus.io.config;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;

import linus.io.config.exception.InvalidConfigException;

public class SimpleConfigWriter extends ConfigWriter{

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
	}

	public SimpleConfigWriter(OutputStream out, ConfigIOChars chars) {
		this(out);
		setConfigIOChars(chars);
	}

	@Override
	@Deprecated
	public void writeConfig(Config<?> cfg) {
		if(cfg instanceof SingleConfig<?>)
			writeConfig((SingleConfig<?>) cfg);
		else if(cfg instanceof MultipleConfig<?>)
			writeConfig((MultipleConfig<?>) cfg);
		else
			throw new InvalidConfigException("");
	}

	public void writeConfig(SingleConfig<?> sc){
		writer.println(sc.writeSimple());
	}

	public void writeConfig(MultipleConfig<?> mc){
		for(String s : mc.writeSimple())
			writer.println(s);
	}
}
