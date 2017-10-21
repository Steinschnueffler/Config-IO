package linus.io.config;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import linus.io.config.configs.Config;
import linus.io.config.configs.ConfigBase;
import linus.io.config.configs.MultipleStringConfig;
import linus.io.config.configs.SingleStringConfig;

public class SimpleConfigReader implements ConfigReader{

	private final Scanner reader;
	private Config<?> buffer;
	private String lineBuffer;
	private InputStream source;

	public SimpleConfigReader(String pathName) throws FileNotFoundException {
		this(new File(pathName));
	}

	public SimpleConfigReader(FileDescriptor fd) {
		this(new FileInputStream(fd));
	}

	public SimpleConfigReader(File f) throws FileNotFoundException {
		this(new FileInputStream(f));
	}

	public SimpleConfigReader(InputStream in) {
		reader = new Scanner(in);
		source = in;
		buffer = nextConfig();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ConfigBase> E next(){
		E cfg = (E) buffer;
		try{
			buffer = nextConfig();
		}catch(ReaderFinishedException rfe){
			buffer = null;
		}
		return cfg;
	}

	private Config<?> nextConfig() throws ReaderFinishedException{
		if(!reader.hasNext()) throw new ReaderFinishedException();
		String nextLine = lineBuffer;
		while(nextLine.startsWith("#") || nextLine.trim().length() == 0){
			if(!reader.hasNext()) throw new ReaderFinishedException();
			nextLine = reader.nextLine();
		}

		String name = nextLine.substring(0, nextLine.indexOf("=")).trim();
		String singleValue = nextLine.substring(nextLine.indexOf("=") + 1, nextLine.length()).trim();
		lineBuffer = reader.nextLine();
		if(singleValue.length() != 0){
			return new SingleStringConfig(name, singleValue);
		}
		ArrayList<String> values = new ArrayList<>();
		while(lineBuffer.trim().startsWith("-")){
			values.add(lineBuffer.substring(lineBuffer.indexOf("-")).trim());
			while(lineBuffer.startsWith("#") || nextLine.trim().length() == 0)
				lineBuffer = reader.nextLine();
		}
		return new MultipleStringConfig(singleValue, values.toArray(new String[values.size()]));
	}

	@Override
	public boolean hasNext(){
		return buffer != null;
	}

	@Override
	public void close(){
		reader.close();
	}

	@Override
	public InputStream getSource() {
		return source;
	}

}
