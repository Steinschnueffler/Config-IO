package linus.io.config;

import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

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
public abstract class ConfigWriter implements Closeable, Flushable, Appendable{

	protected OutputStream source;
	protected ConfigIOChars chars = ConfigIOChars.getDefault();
	protected PrintWriter writer;

	/**
	 * Creates a new abstract ConfigWriter to the given source
	 *
	 * @param source
	 */
	public ConfigWriter(OutputStream source) {
		this.source = source;
		writer = new PrintWriter(source);
	}

	/**
	 *Writes a Info to the given File. It starts with a info-start-char
	 *(usally #) and then the given String. This means that this line is only
	 *for the User to read and will be ignored by ConfigReaders
	 *
	 * @param info the info to be printed
	 */
	public void writeInfo(String info){
		writer.println(chars.getInfoStart() + info);
	}

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
	public void writeln(){
		writer.println();
	}

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
	public void writeHeader(){
		for(String s : chars.getHeader())
			writeInfo(s);
	}

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
		writer.println("%ConfigIOChars : " +chars.getClass().getName());
		this.chars = chars;
	}

	@Override
	public void flush() throws IOException {
		if(writer != null) writer.flush();
		if(source != null) source.flush();
	}

	@Override
	public void close() throws IOException {
		flush();
		if(writer != null) writer.close();
		if(source != null) source.close();
	}

	@Override
	public Appendable append(char c) throws IOException {
		writer.write(c);
		return this;
	}

	@Override
	public Appendable append(CharSequence csq) throws IOException {
		writer.append(csq);
		return this;
	}

	@Override
	public Appendable append(CharSequence csq, int start, int end) throws IOException {
		writer.append(csq, start, end);
		return this;
	}


}
