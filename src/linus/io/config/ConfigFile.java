package linus.io.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;

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

}
