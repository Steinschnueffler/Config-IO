package linus.io.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import linus.io.config.configs.Config;

/**
 *This class can write {@link Config}s in a {@link ComplexConfigReader} and user readable form.
 *
 * @author Linus Dierheimer
 * @since java 1.5
 * @version 1.0
 * @see ComplexConfigReader
 * @see ConfigFile
 */
public class ComplexConfigWriter implements ConfigWriter{

	private final PrintWriter writer;
	private ConfigIOChars chars;

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
		writer = new PrintWriter(out);
		useConfigIOChars(chars);
	}

	@Override
	public void writeInfo(String info){
		writer.println(chars.getInfoStart() + info);
	}

	@Override
	public void writeConfig(Config<?> cfg){

		if(cfg.getName().startsWith("" +chars.getClassEnd()))
			throw new InvalidConfigException("Name starts with the same character as class End: " + chars.getClassEnd());

		writer.println(chars.getClassStart() + cfg.getClass().getName());
		for(String s : cfg.write())
			writer.println(s);
		writer.println(chars.getClassEnd());
	}

	@Override
	public void writeln(){
		writer.println();
	}

	/**
	 * Sets the used {@link ConfigIOChars} to the given.
	 *
	 * @param chars the used ConfigIOChars
	 */
	public void useConfigIOChars(ConfigIOChars chars){
		this.chars = chars;
		writer.println("%ConfigIOChars : " +chars.getClass().getName());
	}

	/**
	 *Returns the actually used {@link ConfigIOChars}
	 *
	 * @return the used CofigIOChars
	 */
	public ConfigIOChars getConfigIOChars(){
		return chars;
	}

	@Override
	public void close(){
		writer.flush();
		writer.close();
	}

}
