package linus.io.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import linus.io.config.configs.Config;
import linus.io.config.configs.ConfigBase;

public class ThreadedConfigReader implements ConfigReader{

	private ConfigReader reader;
	private ArrayList<Config<?>> cfgs = new ArrayList<>();
	private ReadingThread thread = new ReadingThread();

	public ThreadedConfigReader(ConfigReader reader) {
		if(reader instanceof ThreadedConfigReader)
			throw new InvalidConfigReaderException("ThreadedConfigReader cannot be constructed with another ThreadedConfigReader");
		this.reader = reader;
	}

	public ThreadedConfigReader(ConfigReader reader, boolean autoStart) {
		this(reader);
		if(autoStart) start();
	}

	public ConfigReader getReader(){
		return reader;
	}

	public void start(){
		thread.start();
	}

	public boolean isReading(){
		return thread.isAlive();
	}

	public void join() throws InterruptedException{
		thread.join();
	}

	@Override
	public void close() throws IOException {
		try {
			join();
		} catch (InterruptedException e) {
			throw new IOException(e);
		}
		reader.close();
	}

	@Override
	@Deprecated
	public <E extends ConfigBase> E next() {
		return reader.next();
	}

	@Override
	@Deprecated
	public boolean hasNext() {
		return reader.hasNext();
	}

	public Config<?>[] getAll(){
		return cfgs.toArray(new Config<?>[cfgs.size()]);
	}

	private class ReadingThread extends Thread{

		@Override
		public void run() {
			while(hasNext())
				cfgs.add(next());
		}

	}

	@Override
	public InputStream getSource() {
		return reader.getSource();
	}

}
