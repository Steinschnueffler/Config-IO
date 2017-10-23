package linus.io.config;

import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.InputStream;

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
 * @author Linus Dierheimer
 *
 */
public abstract interface ConfigReader extends Closeable{

	/**
	 * Reads the next {@link Config} and returns it with
	 * automatically class cast. If there doesn't
	 * exists a next Config, it will return null.
	 *
	 * @param <E> - the Config Type
	 * @return the next Config<?>
	 */
	public abstract <E extends ConfigBase> E next();

	/**
	 * Returns if the reader has a next {@link Config} to read.
	 *
	 * @return true if the reader has a next Config
	 */
	public abstract boolean hasNext();

	/**
	 * Returns the source of the Reader as {@link InputStream}. Usually its the InputStream
	 * with that the Reader was constructed or a {@link FileInputStream} of the {@link File} or
	 * {@link FileDescriptor} on that the Reader reads.
	 *
	 * @return the source of the Reader
	 */
	public abstract InputStream getSource();
}
