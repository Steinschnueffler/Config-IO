package linus.io.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ThreadedConfigWriter implements ConfigWriter{

	private ConfigWriter writer;
	private ArrayList<Config<?>> cfgs = new ArrayList<>();
	private WriterThread thread = new WriterThread();

	public ThreadedConfigWriter(ConfigWriter writer) {
		this.writer = writer;
	}

	public ThreadedConfigWriter(ConfigWriter writer, Config<?>... cfgs) {
		this(writer);
		writeAll(cfgs);
	}

	public ConfigWriter getWriter(){
		return writer;
	}

	public void start(){
		thread.start();
	}

	public boolean isWriting(){
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
		writer.close();
	}

	@Override
	@Deprecated
	public void writeInfo(String info) {
		writer.writeInfo(info);
	}

	@Override
	@Deprecated
	public void writeConfig(Config<?> cfg) {
		writer.writeConfig(cfg);
	}

	@Override
	@Deprecated
	public void writeln() {
		writer.writeln();
	}

	public boolean hasUnwritten(){
		return cfgs.size() > 0;
	}

	public void writeAll(Config<?>... cfgs){
		this.cfgs.addAll(Arrays.asList(cfgs));
	}

	private class WriterThread extends Thread{

		@Override
		public void run() {
			for(Config<?> cfg : cfgs)
				writeConfig(cfg);
			cfgs.clear();
		}

	}

}
