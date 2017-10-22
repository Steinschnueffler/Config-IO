package linus.io.config;

import java.io.Closeable;
import java.io.OutputStream;

/**
*
*This Interface is the root of all ConfigWriters. It contains the
*typically abstract writing Methods with speciality for {@link Config}s.
*It also is an extension of {@link Closeable}, so the writers can be closed.
*
* @version 1.0
* @since Java 1.5
* @see SimpleConfigWriter
* @see ComplexConfigWriter
* @see ThreadedConfigWriter
* @author Linus Dierheimer
*
*/
public abstract interface ConfigWriter extends Closeable{

	/**
	 *Writes a Info to the given File. It starts with a info-start-char
	 *(usally #) and then the given String. This means that this line is only
	 *for the User to read and will be ignored by ConfigReaders
	 *
	 * @param info the info to be printed
	 */
	public abstract void writeInfo(String info);

	/**
	 * Writes the given {@link Config} to the given File. Usally it
	 * prints the lines from Config.write().
	 *
	 * @param cfg the Config that should be written
	 */
	public abstract void writeConfig(Config<?> cfg);

	/**
	 * Writes a new Line to the given File.
	 */
	public abstract void writeln();

	public abstract OutputStream getSource();
}
