package linus.io.config.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.Scanner;

import linus.io.config.io.ComplexConfigReader;
import linus.io.config.io.ComplexConfigWriter;
import linus.io.config.io.ConfigReader;
import linus.io.config.io.SerializingConfigReader;
import linus.io.config.io.SerializingConfigWriter;
import linus.io.config.io.SimpleConfigReader;
import linus.io.config.io.SimpleConfigWriter;

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
	public ConfigReader getFittingReader() throws FileNotFoundException{
		Scanner s = new Scanner(this);
		while(s.hasNextLine()) {
			String line = s.nextLine();
			if(line.startsWith("%FittingReader")) {
				String classPath = line.substring(line.indexOf(":") + 1, line.length()).trim();
				try {
					ConfigReader cr = loadConfigReader(classPath);
					s.close();
					return cr;
				} catch (ReflectiveOperationException e) {
					continue;
				}
			}
		}
		s.close();
		return getComplexReader();
	}

	private ConfigReader loadConfigReader(String classPath) throws ReflectiveOperationException, FileNotFoundException{
		if(classPath.startsWith("linus.io.config")){
			if(classPath.equalsIgnoreCase("linus.io.config.SimpleConfigReader"))
				return new SimpleConfigReader(this);
			if(classPath.equalsIgnoreCase("linus.io.config.ComplexConfigReader"))
				return new ComplexConfigReader(this);
			if(classPath.equalsIgnoreCase("linus.io.config.SerializingConfigReader"))
				return new SerializingConfigReader(this);
		}

		Class<?> clazz = Class.forName(classPath);
		return (ConfigReader) clazz.getConstructor().newInstance();
	}

}
