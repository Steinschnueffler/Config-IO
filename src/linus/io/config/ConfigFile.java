package linus.io.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.Scanner;

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

	public SerializingConfigWriter getSerializingWriter() throws FileNotFoundException{
		return new SerializingConfigWriter(this);
	}

	public SerializingConfigReader getSerializingReader() throws FileNotFoundException{
		return new SerializingConfigReader(this);
	}

	public ConfigReader getFittingReader() throws FileNotFoundException, ReflectiveOperationException{
		Scanner s = new Scanner(this);
		boolean hasNext = s.hasNext();
		String line = s.nextLine();
		s.close();
		if(!hasNext) return getComplexReader();
		if(!line.startsWith("%PreferedReader :")) return getComplexReader();
		String readerClass = line.substring(line.indexOf(":")).trim();
		return ReflectiveConfigLoader.loadConfigReader(readerClass, this);
	}

}
