package linus.io.config.io;

import java.io.Closeable;
import java.io.Flushable;

import linus.io.config.Config;
import linus.io.config.exception.ConfigWriteEexception;

public interface ConfigWriter extends Closeable, Flushable{

	public void writeConfig(Config<?> cfg) throws ConfigWriteEexception;
	
	public void writeInfo(String info) throws ConfigWriteEexception;
	
	public void writeln() throws ConfigWriteEexception;
	
	public Object getSource();

	public ConfigReader getFittingReader();
}
