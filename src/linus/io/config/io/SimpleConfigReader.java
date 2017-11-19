package linus.io.config.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import linus.io.config.ConfigBase;
import linus.io.config.exception.ConfigReadException;
import linus.io.config.util.ConfigHolder;

public class SimpleConfigReader extends SimpleConfigReaderBase{

	private Exception ex = null;
	
	public SimpleConfigReader(String pathName) {
		this(Paths.get(pathName));
	}
	
	public SimpleConfigReader(File f) {
		this(f.toPath());
	}
	
	public SimpleConfigReader(InputStream in) {
		super(new InputStreamReader(in));
	}
	
	public SimpleConfigReader(FileDescriptor fd) {
		this(new FileInputStream(fd));
	}
	
	public SimpleConfigReader(Path path){
		super(getReader(path));
	}

	private static BufferedReader getReader(Path p) {
		try {
			return Files.newBufferedReader(p);
		} catch (IOException e) {
			return new BufferedReader(new StringReader(""));
		}
	}
	
	public boolean checkException() {
		return ex != null;
	}
	
	public Exception resetException() {
		Exception temp = ex;
		ex = null;
		return temp;
	}
	
	public Exception getException() {
		return ex;
	}
	
	@Override
	public <E extends ConfigBase> E nextConfig(){
		try {
			return super.nextConfig();
		} catch (ConfigReadException e) {
			this.ex = e;
			return null;
		}
	}
	
	@Override
	public ConfigHolder readAll(){
		try {
			return super.readAll();
		} catch (ConfigReadException e) {
			this.ex = e;
			return ConfigHolder.EMPTY_HOLDER;
		}
	}
}
