package linus.io.config;

import java.io.Closeable;
import java.io.FileOutputStream;
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
* @see ConfigReader
* @author Linus Dierheimer
*
*/
public abstract class ConfigWriter implements Closeable{

	protected OutputStream source;
	protected ConfigIOChars chars = ConfigIOChars.getDefault();

	/**
	 * Creates a new abstract ConfigWriter to the given source
	 *
	 * @param source
	 */
	public ConfigWriter(OutputStream source) {
		this.source = source;
	}

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
	 * Writes a new Line to the given Source with no chars in it.
	 */
	public abstract void writeln();

	/**
	 * returns the Source of the Writer as an {@link OutputStream}. Usally it is the
	 * Stream with that it was constructed or a {@link FileOutputStream} o the File
	 * Source.
	 *
	 * @return the source of the writer
	 */
	public OutputStream getSource(){
		return source;
	}

	/**
	 * Writes a Head information to the source of the Reader, in that
	 * are copyrights and Infos how to use the Config File. It is definit
	 * in the {@link ConfigIOChars} so it can be changed with  {@link ConfigIOCharsBuilder}.
	 */
	public abstract void writeHeader();

	/**
	 * Returns the {@link ConfigIOChars} that are used by the reader.
	 *
	 * @return the actual ConfigIOChars
	 */
	public ConfigIOChars getConfigIOChars(){
		return chars;
	}

	/**
	 * Sets the used {@link ConfigIOChars} to the given one. This will
	 * make the Writer writing a line with the Information about it, so
	 * the reader is able to read further exactly.
	 *
	 * @param chars the new ConfigIOChars used by the Writer
	 */
	public void setConfigIOChars(ConfigIOChars chars){
		this.chars = chars;
	}
}
