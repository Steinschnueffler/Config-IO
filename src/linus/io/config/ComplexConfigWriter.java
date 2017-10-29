package linus.io.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import linus.io.config.exception.ConfigOperationException;
import linus.io.config.exception.ConfigWriteEexception;
import linus.io.config.util.ConfigFile;

/**
 *This class can write {@link Config}s in a {@link ComplexConfigReader} and user readable form.
 *
 * @author Linus Dierheimer
 * @since java 1.5
 * @version 1.0
 * @see ComplexConfigReader
 * @see ConfigFile
 */
public class ComplexConfigWriter extends ComplexConfigWriterBase{

	private Exception e = null;
	
	public Exception checkException() {
		Exception akt = e;
		e = null;
		return akt;
	}
	
	/**
	 * Creates a new ConfigWriter of the given File
	 *
	 * @param f
	 * @throws FileNotFoundException if the File doesn't exists
	 */
	public ComplexConfigWriter(File f) throws FileNotFoundException {
		this(new FileOutputStream(f));
	}

	/**
	 *Creates a new ConfigWriter of the given File. The used {@link ConfigIOChars} will
	 *be the given
	 *
	 * @param f the File
	 * @param chars the ConfigIOChars
	 * @throws FileNotFoundException if the File doesn't exists
	 */
	public ComplexConfigWriter(File f, ConfigIOChars chars) throws FileNotFoundException{
		this(new FileOutputStream(f), chars);
	}

	/**
	 * Creates a new ConfigWriter to the File of the abstract given path Name
	 *
	 * @param pathName
	 * @throws FileNotFoundException if the File doesn't exists
	 */
	public ComplexConfigWriter(String pathName) throws FileNotFoundException{
		this(new File(pathName));
	}

	/**
	 * Creates a new ConfigWriter to the File of the abstract given path Name.
	 * The used {@link ConfigIOChars} will be the given
	 *
	 * @param pathName
	 * @throws FileNotFoundException if the File doesn't exists
	 */
	public ComplexConfigWriter(String pathName, ConfigIOChars chars) throws FileNotFoundException{
		this(new File(pathName), chars);
	}

	/**
	 * Creates a new ConfigWriter to the given {@link OutputStream}.
	 *
	 * @param out the OutputStream
	 */
	public ComplexConfigWriter(OutputStream out){
		this(out, ConfigIOChars.getDefault());
	}

	/**
	 * Creates a new ConfigWriter to the given {@link OutputStream}.
	 * The used {@link ConfigIOChars} will be the given
	 *
	 * @param out the OutputStream
	 */
	public ComplexConfigWriter(OutputStream out, ConfigIOChars chars){
		super(out);
	}
	
	@Override
	public void close(){
		try {
			super.close();
		} catch (IOException e) {
			e = new ConfigWriteEexception(e);
		}
	}
	
	@Override
	public void setConfigIOChars(ConfigIOChars chars){
		try {
			super.setConfigIOChars(chars);
		} catch (IOException e) {
			e = new ConfigWriteEexception(e);
		}
	}
	
	@Override
	public void flush(){
		try {
			super.flush();
		} catch (IOException e) {
			throw new ConfigOperationException(e);
		}
	}
	
	@Override
	public void writeConfig(Config<?> cfg){
		try {
			super.writeConfig(cfg);
		} catch (IOException e) {
			throw new ConfigOperationException(e);
		}
	}
	
	@Override
	public void writeHeader(){
		try {
			super.writeHeader();
		} catch (IOException e) {
			throw new ConfigOperationException(e);
		}
	}
	
	@Override
	public void writeInfo(String info){
		try {
			super.writeInfo(info);
		} catch (IOException e) {
			throw new ConfigOperationException(e);
		}
	}
	
	@Override
	public void writeln(){
		try {
			super.writeln();
		} catch (IOException e) {
			throw new ConfigOperationException(e);
		}
	}
}
