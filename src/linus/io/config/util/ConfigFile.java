package linus.io.config.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.Scanner;

import linus.io.config.ComplexConfigReader;
import linus.io.config.ComplexConfigWriter;
import linus.io.config.ConfigReader;
import linus.io.config.SerializingConfigReader;
import linus.io.config.SerializingConfigWriter;
import linus.io.config.SimpleConfigReader;
import linus.io.config.SimpleConfigWriter;

/**
 *
 * This representation of {@link File} is for a better using of
 * {@link ComplexConfigWriter} and {@link ComplexConfigReader}.
 *
 * @author Linus Dierheiemer
 * @since java 1.5
 * @version 1.0
 *
 */
public class ConfigFile extends File{
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new ConfigFile by the given abstract pathName.
	 * @param path - the abstract PathName of the File
	 */
	public ConfigFile(String path){
		super(path);
	}

	/**
	 * Creates a new ConfigFile by the given URI
	 * @param uri the URI of the File
	 */
	public ConfigFile(URI uri) {
		super(uri);
	}

	/**
	 * this method returns a new {@link ComplexConfigReader} with the position
	 * at the beginn of the file.
	 * @return a new ConfigReader
	 * @throws FileNotFoundException if the File doesn't exists
	 */
	public ComplexConfigReader getComplexReader() throws FileNotFoundException{
		return new ComplexConfigReader(this);
	}

	/**
	 * this method returns a new {@link ComplexConfigWriter} with the position
	 * at the beginn of the file.
	 *
	 * @return a new ConfigWriter
	 * @throws FileNotFoundException if the File doesn't exists
	 */
	public ComplexConfigWriter getComplexWriter() throws FileNotFoundException{
		return new ComplexConfigWriter(this);
	}


	/**
	 * this method returns a new {@link SimpleConfigReader} with the position
	 * at the beginn of the file.
	 * @return a new ConfigReader
	 * @throws FileNotFoundException if the File doesn't exists
	 */
	public SimpleConfigReader getSimpleReader() throws FileNotFoundException{
		return new SimpleConfigReader(this);
	}

	/**
	 * this method returns a new {@link SimpleConfigWriter} with the position
	 * at the beginn of the file.
	 *
	 * @return a new ConfigWriter
	 * @throws FileNotFoundException if the File doesn't exists
	 */
	public SimpleConfigWriter getSimpleWriter() throws FileNotFoundException{
		return new SimpleConfigWriter(this);
	}

	/**
	 * this method returns a new {@link SerializingConfigWriter} with the
	 * position at the beginn of the file.
	 *
	 * @return a new ConfigWriter
	 * @throws FileNotFoundException if the File doesn't exists
	 */
	public SerializingConfigWriter getSerializingWriter() throws FileNotFoundException{
		return new SerializingConfigWriter(this);
	}

	/**
	 * this method returns a new {@link SerializingConfigReader} with the
	 * position at the beginn of the file.
	 *
	 * @return a new ConfigReader
	 * @throws FileNotFoundException if the File doesn't exists
	 */
	public SerializingConfigReader getSerializingReader() throws FileNotFoundException{
		return new SerializingConfigReader(this);
	}

	/**
	 * Trys to return the reader, with that related Writer the File was written. If it
	 * isn't succesfull, it will return {@link ComplexConfigReader}.
	 *
	 * @return a fitting {@link ConfigReader} to the File
	 * @throws FileNotFoundException
	 * @throws ReflectiveOperationException
	 */
	public ConfigReader getFittingReader() throws FileNotFoundException, ReflectiveOperationException{
		Scanner s = new Scanner(this);
		boolean hasNext = s.hasNext();
		String line = s.nextLine();
		s.close();
		if(!hasNext) return getComplexReader();
		if(!line.startsWith("%PreferedReader :")) return getComplexReader();
		String readerClass = line.substring(line.indexOf(":")).trim();
		return loadConfigReader(readerClass, new FileInputStream(this));
	}

	private ConfigReader loadConfigReader(String classPath, InputStream source) throws ReflectiveOperationException{
		if(classPath.startsWith("linus.io.config")){
			if(classPath.equals("linus.io.config.SimpleConfigReader"))
				return new SimpleConfigReader(source);
			if(classPath.equals("linus.io.config.ComplexConfigReader"))
				return new ComplexConfigReader(source);
			if(classPath.equals("linus.io.config.SerializingConfigReader"))
				return new SerializingConfigReader(source);
		}

		Class<?> clazz = Class.forName(classPath);
		return (ConfigReader) clazz.getConstructor().newInstance();
	}

}
