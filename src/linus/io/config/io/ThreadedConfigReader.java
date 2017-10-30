package linus.io.config.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import linus.io.config.base.Config;
import linus.io.config.base.ConfigReader;
import linus.io.config.exception.ConfigReadException;

public class ThreadedConfigReader implements Closeable{

	private ConfigReader reader;
	private ConfigReader originReader;
	private ArrayList<Config<?>> cfgs = new ArrayList<>();
	private ReadingThread thread;

	public ThreadedConfigReader(ConfigReader reader) {
		this.originReader = reader;
	}

	public ThreadedConfigReader(ConfigReader reader, boolean autoStart) {
		this(reader);
		if(autoStart) start();
	}

	public ConfigReader getReader(){
		return originReader;
	}

	public void start(){
		reader = originReader;
		thread = new ReadingThread();
		thread.start();
	}

	public boolean isReading(){
		return thread != null ? thread.isAlive() : false;
	}

	public void join() throws InterruptedException{
		if(thread != null) thread.join();
	}

	@Override
	public void close() throws IOException {
		try {
			join();
		} catch (InterruptedException e) {
			throw new IOException(e);
		}
		thread = null;
		reader.close();
		reader = null;
	}

	public Config<?>[] getAll(){
		return cfgs.toArray(new Config<?>[cfgs.size()]);
	}

	private class ReadingThread extends Thread{

		@Override
		public void run() {
			while(reader.hasNext())
				try {
					cfgs.add(reader.next());
				} catch (ConfigReadException | ReflectiveOperationException e) {}
		}

	}

	public InputStream getSource() {
		return reader.getSource();
	}

}
