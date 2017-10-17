package linus.io.config;

import java.io.Closeable;

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

}
