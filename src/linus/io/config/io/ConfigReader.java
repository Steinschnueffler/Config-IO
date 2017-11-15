package linus.io.config.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import linus.io.config.Config;
import linus.io.config.ConfigBase;
import linus.io.config.exception.ConfigReadException;
import linus.io.config.exception.GeneratedConfigException;
import linus.io.config.exception.InvalidConfigReaderException;
import linus.io.config.util.ConfigHolder;

/**
 *
 *This Interface is the root of all ConfigReaders. It contains the
 *typically abstract raeding Methods with speciality for {@link Config}s.
 *It also is an extension of {@link Closeable}, so the readers can be closed.
 *
 * @version 1.0
 * @since Java 1.5
 * @see SimpleConfigReader
 * @see ComplexConfigReader
 * @see ThreadedConfigReader
 * @see ConfigWriter
 * @author Linus Dierheimer
 *
 */
public abstract class ConfigReader implements Closeable{

	protected InputStream source;
	protected ConfigIOChars chars;
	protected BufferedReader reader;
	protected Config<?> buffer;

	/**
	 * Creates  new abstract ConfigReader on the given source.
	 * @param source
	 * @throws Exception 
	 */
	public ConfigReader(InputStream source){
		if(source == null) source = System.in;
		this.source = source;
		reader = new BufferedReader(new InputStreamReader(source));
		try {
			buffer = nextConfig();
		} catch (ReflectiveOperationException | IOException e) {
			buffer = null;
		}
	}

	/**
	 * Reads the next {@link Config} and returns it with
	 * automatically class cast. If there doesn't
	 * exists a next Config, it will return null.
	 *
	 * @param <E> - the Config Type
	 * @return the next Config<?>
	 * @throws ReflectiveOperationException 
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public <E extends ConfigBase> E next() throws ConfigReadException, ReflectiveOperationException{
		Config<?> cfg = buffer;
		try {
			buffer = nextConfig();
		} catch (IOException e) {
			throw new ConfigReadException(e);
		}
		return (E) cfg;
	};

	/**
	 * Returns if the reader has a next {@link Config} to read.
	 *
	 * @return true if the reader has a next Config
	 */
	public boolean hasNext(){
		return buffer != null;
	}

	protected abstract Config<?> nextConfig() throws ConfigReadException, ReflectiveOperationException;

	/**
	 * Returns the source of the Reader as {@link InputStream}. Usually its the InputStream
	 * with that the Reader was constructed or a {@link FileInputStream} of the {@link File} or
	 * {@link FileDescriptor} on that the Reader reads.
	 *
	 * @return the source of the Reader
	 */
	public InputStream getSource(){
		return source;
	}

	/**
	 * Returns the actual used {@link ConfigIOChars}. The Default is ConfigIOChars.getDefault.
	 * They might be changed by infos in the Source.
	 *
	 * @return the actual ConfigIOChars
	 */
	public ConfigIOChars getConfigIOChars(){
		return chars;
	}

	public InvalidConfigReaderException createException(String msg){
		return new InvalidConfigReaderException(toString(), new GeneratedConfigException(msg));
	}

	public InvalidConfigReaderException createException(){
		return createException("");
	}

	@Override
	public void close() throws ConfigReadException {
			try {
				if(source != null) source.close();
				if(reader != null) reader.close();
			} catch (IOException e) {
				throw new ConfigReadException(e);
			}
	}

	public ConfigHolder readAll() throws ConfigReadException {
		return ConfigHolder.loadFromReader(this);
	}
}
