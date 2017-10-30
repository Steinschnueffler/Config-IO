package linus.io.config.base;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import linus.io.config.exception.ConfigOperationException;
import linus.io.config.exception.ConfigWriteEexception;
import linus.io.config.io.ComplexConfigWriter;
import linus.io.config.io.ConfigIOChars;
import linus.io.config.io.SimpleConfigWriter;
import linus.io.config.io.ThreadedConfigWriter;
import linus.io.config.util.ConfigHolder;

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
	protected ConfigIOChars chars;
	protected BufferedWriter writer;

	/**
	 * Creates a new abstract ConfigWriter to the given source
	 *
	 * @param source
	 */
	public ConfigWriter(OutputStream source, ConfigIOChars chars) {
		if(source == null) source = System.out;
		this.source = source;
		writer = new BufferedWriter(new OutputStreamWriter(source));

		if(getFittingReader() != null)
			try {
				writeln("%FittingReader : " +getFittingReader().getName());
			} catch (IOException e) {}
		try {
			setConfigIOChars(chars);
		} catch (IOException e) {
			chars = ConfigIOChars.getDefault();
		}
	}

	/**
	 * Creates a new abstract ConfigWriter to the given source
	 *
	 * @param source
	 */
	public ConfigWriter(OutputStream source) {
		this(source, ConfigIOChars.getDefault());
	}

	/**
	 *Writes a Info to the given File. It starts with a info-start-char
	 *(usally #) and then the given String. This means that this line is only
	 *for the User to read and will be ignored by ConfigReaders
	 *
	 * @param info the info to be printed
	 */
	public void writeInfo(String info) throws ConfigWriteEexception{
		if(info == null) info = "null";
		try {
			writeln(chars.getInfoStart() + info);
		} catch (IOException e) {
			throw new ConfigWriteEexception(e);
		}
	}

	/**
	 * Writes the given {@link Config} to the given File. Usally it
	 * prints the lines from Config.write().
	 *
	 * @param cfg the Config that should be written
	 */
	public abstract void writeConfig(Config<?> cfg) throws ConfigWriteEexception;

	/**
	 * Writes a new Line to the given Source with no chars in it.
	 */
	public void writeln() throws ConfigWriteEexception{
		writeln("");
	}
	
	/**
	 * Writes all Configs of an {@link ConfigHolder}.
	 * 
	 * @param holder
	 * @throws ConfigWriteEexception
	 */
	public void write(ConfigHolder holder) throws ConfigWriteEexception {
		for(Config<?> cfg : holder)
			writeConfig(cfg);
	}
	
	protected void writeln(String str) throws ConfigWriteEexception{
		try {
			writer.write(str);
			writer.newLine();
		} catch (IOException e) {
			throw new ConfigWriteEexception(e);
		}
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
	 * @throws IOException 
	 */
	public void writeHeader() throws ConfigWriteEexception{
		if(chars.getHeader() == null) return;
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
	public void setConfigIOChars(ConfigIOChars chars) throws ConfigWriteEexception{
		try {
			writeln("%ConfigIOChars : " +chars.getClass().getName());
		} catch (IOException e) {
			throw new ConfigWriteEexception(e);
		}
		this.chars = chars;
	}

	@Override
	public void flush() throws ConfigWriteEexception {
		try {
			if(writer != null) writer.flush();
			if(source != null) source.flush();
		}catch(IOException e) {
			throw new ConfigWriteEexception(e);
		}
	}

	@Override
	public void close() throws ConfigWriteEexception {
		flush();
		try {
			if(writer != null) writer.close();
			if(source != null) source.close();
		}catch(IOException e) {
			throw new ConfigWriteEexception(e);
		}
	}

	@Override
	public Appendable append(char c) throws ConfigWriteEexception {
		try {
			writeInfo("" +c);
		} catch (IOException e) {
			throw new ConfigWriteEexception(e);
		}
		return this;
	}

	@Override
	public Appendable append(CharSequence csq) throws ConfigWriteEexception {
		if(csq == null) csq = "null";
		try {
			writeInfo(csq.toString());
		} catch (IOException e) {
			throw new ConfigWriteEexception(e);
		}
		return this;
	}

	@Override
	public Appendable append(CharSequence csq, int start, int end) throws ConfigWriteEexception {
		if(csq.length() == 0) return this;
		if(start < 0) throw new ConfigOperationException("start < 0");
		if(end > csq.length()) throw new ConfigOperationException("end > csq.length");
		try {
			writeInfo(csq.subSequence(start, end).toString());
		} catch (IOException e) {
			throw new ConfigWriteEexception(e);
		}
		return this;
	}

	public abstract Class<? extends ConfigReader> getFittingReader();

}
