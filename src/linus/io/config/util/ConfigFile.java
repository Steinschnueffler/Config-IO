package linus.io.config.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import linus.io.config.io.AbstractConfigReader;
import linus.io.config.io.ConfigReader;
import linus.io.config.io.SimpleConfigReader;

/**
 *
 * @author Linus Dierheiemer
 * @since java 1.5
 * @version 1.0
 *
 */
public class ConfigFile{
	
	public static final String READER_INFO_START = "%";
	public static final char READER_INFO_SEPARATOR = '=';
	public static final String FITTING_READER_INFO = READER_INFO_START +"FittingReader " +READER_INFO_SEPARATOR;
	public static final char CLASS_PATH_START = '@';
	public static final String CLASS_PATH_END = CLASS_PATH_START +"Finish";
	public static final String COMMENT_START = "#";
	
	protected static final FileSystem FS = FileSystems.getDefault();
	
	protected Path path = null;
	
	public ConfigFile(String pathName) {
		path = FS.getPath(pathName);
	}
	
	public ConfigFile(URI uri) {
		path = Paths.get(uri);
	}
	
	public ConfigFile(Path p) {
		this.path = p;
	}
	
	public boolean createNewFile() throws IOException {
		if(Files.exists(path)) return false;
		path = Files.createFile(path);
		return true;
	}
	
	public boolean delete() throws IOException {
		return Files.deleteIfExists(path);
	}
	
	public boolean exists() {
		return Files.exists(path);
	}

	@SuppressWarnings("unchecked")
	public <E extends ConfigReader> E getFittingReader() throws IOException {
		createNewFile();
		BufferedReader br = Files.newBufferedReader(path);
		while(!br.ready()) {
			String line = br.readLine().trim();
			if(line.startsWith(FITTING_READER_INFO)) {
				ConfigReader cr = loadReader(line.substring(line.indexOf(READER_INFO_SEPARATOR) + 1));
				if(cr != null) return (E) cr;
			}
		}
		return (E) getSimpleReader();
	}

	private AbstractConfigReader loadReader(String pathName) throws IOException {
		if(pathName.equalsIgnoreCase("linus.io.config.io.SimpleConfigReader"))
			return getSimpleReader();
		return null;
	}

	private SimpleConfigReader getSimpleReader() throws FileNotFoundException {
		return new SimpleConfigReader(path.toString());
	}
	
}
