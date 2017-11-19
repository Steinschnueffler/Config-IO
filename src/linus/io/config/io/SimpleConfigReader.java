package linus.io.config.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	
	public SimpleConfigReader(String pathName) {
		this(Paths.get(pathName));
	}
	
	public SimpleConfigReader(File f) {
		super(getReader(f), f);
	}
	
	public SimpleConfigReader(InputStream in) {
		super(new InputStreamReader(in), in);
	}
	
	public SimpleConfigReader(FileDescriptor fd) {
		super(new InputStreamReader(new FileInputStream(fd)), fd);
	}
	
	public SimpleConfigReader(Path path){
		super(getReader(path), path);
	}

	private static BufferedReader getReader(Path p) {
		try {
			return Files.newBufferedReader(p);
		} catch (IOException e) {
			return new BufferedReader(new StringReader(""));
		}
	}
	
	private static BufferedReader getReader(File f) {
		try {
			return new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			return new BufferedReader(new StringReader(""));
		}
	}
	
	public boolean checkException() {
		return lastException != null;
	}
	
	public Exception resetException() {
		Exception temp = lastException;
		lastException = null;
		return temp;
	}
	
	public Exception getException() {
		return lastException;
	}
	
	@Override
	public <E extends ConfigBase> E nextConfig(){
		try {
			return super.nextConfig();
		} catch (ConfigReadException e) {
			this.lastException = e;
			return null;
		}
	}
	
	@Override
	public ConfigHolder readAll(){
		try {
			return super.readAll();
		} catch (ConfigReadException e) {
			this.lastException = e;
			return ConfigHolder.EMPTY_HOLDER;
		}
	}
}
