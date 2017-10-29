package linus.io.config;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import linus.io.config.exception.ConfigWriteEexception;

public class ThreadedConfigWriter implements Closeable{

	private ConfigWriter writer;
	private Vector<WriterLines> lines = new Vector<>();
	private boolean stayAlive = true;
	private WritingThread thread = new WritingThread();

	public ThreadedConfigWriter(ConfigWriter writer) {
		this.writer = writer;
	}

	public ThreadedConfigWriter(ConfigWriter writer, boolean autoStart) {
		this(writer);
		if(autoStart) start();
	}

	public ConfigWriter getWriter(){
		return writer;
	}

	public synchronized void start(){
		thread.start();
	}

	public synchronized void stop(){
		stayAlive = false;
	}

	public boolean isWriting(){
		synchronized (lines) {
			return lines.size() > 0;
		}
	}

	public boolean isAlive(){
		return thread.isAlive();
	}

	public synchronized void join() throws InterruptedException{
		while(isWriting()){}
	}

	@Override
	public void close() throws IOException {
		stayAlive = false;
		try {
			join();
		} catch (InterruptedException e) {
			throw new IOException(e);
		}
		writer.close();
	}

	public void writeInfo(String info) {
		lines.add(new WriterLines(info));
	}

	public void writeConfig(Config<?> cfg) {
		lines.add(new WriterLines(cfg));
	}

	public void writeAll(Config<?>... cfgs){
		for(int i = 0; i < cfgs.length; i++){
			writeConfig(cfgs[i]);
		}
	}

	public void writeln() {
		lines.add(new WriterLines(true));
	}

	private class WriterLines{

		private String info = null;
		private boolean writeln = false;
		private Config<?> cfg = null;

		public WriterLines(String info) {
			this.info = info;
		}

		public WriterLines(boolean writeln) {
			this.writeln = writeln;
		}

		public WriterLines(Config<?> cfg) {
			this.cfg = cfg;
		}
	}

	private class WritingThread extends Thread{

		@Override
		public void run() {
			while (stayAlive) {
				if(lines.size() == 0) continue;
				WriterLines wt = lines.get(0);
				lines.remove(0);
				try {
					if(wt.cfg != null) writer.writeConfig(wt.cfg);
					if(wt.info != null) writer.writeInfo(wt.info);
					if(wt.writeln == true) writer.writeln();
				}catch(ConfigWriteEexception e) {}
			}
		}

	}

	public OutputStream getSource() {
		return writer.getSource();
	}
}
