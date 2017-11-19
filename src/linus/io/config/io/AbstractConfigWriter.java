package linus.io.config.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

import linus.io.config.exception.ConfigWriteEexception;
import linus.io.config.util.ConfigFile;

public abstract class AbstractConfigWriter implements ConfigWriter {

	protected BufferedWriter writer;
	protected Object source;
	
	protected Exception lastException;
	
	public AbstractConfigWriter(Writer writer, Object source) {
		this.writer = writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer);
		this.source = source;
		try {
			writeln(ConfigFile.FITTING_READER_INFO + getFittingReader().getClass().getName());
		} catch (ConfigWriteEexception e) {
			lastException = e;
		}
	}
	
	@Override
	public void writeln() throws ConfigWriteEexception {
		writeln("");
	}
	
	protected final void writeln(String str) throws ConfigWriteEexception{
		try {
			writer.write(str);
			writer.newLine();
		} catch (IOException e) {
			lastException = e;
			throw new ConfigWriteEexception(e);
		}
	}
	
	@Override
	public void close() throws IOException {
		flush();
		if(writer != null) writer.close();
	} 
	
	@Override
	public void flush() throws IOException {
		if(writer != null) writer.flush();
	}
	
	@Override
	public void writeInfo(String info) throws ConfigWriteEexception {
		writeln(ConfigFile.COMMENT_START + info);
	}
	
	@Override
	public Object getSource() {
		return source;
	}
	
}
