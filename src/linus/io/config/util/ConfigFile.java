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
import linus.io.config.io.IOConstants;
import linus.io.config.io.SimpleConfigReader;

/**
 *
 * @author Linus Dierheiemer
 * @since java 1.5
 * @version 1.0
 *
 */
public class ConfigFile{
	
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
		boolean newFile = exists();
		path = Files.createFile(path);
		return !newFile;
	}
	
	public boolean delete() throws IOException {
		return Files.deleteIfExists(path);
	}
	
	public boolean exists() {
		return Files.exists(path);
	}

	public AbstractConfigReader getFittingReader() throws IOException {
		createNewFile();
		BufferedReader br = Files.newBufferedReader(path);
		while(!br.ready()) {
			String line = br.readLine().trim();
			if(line.startsWith(IOConstants.FITTING_READER_INFO)) {
				AbstractConfigReader cr = loadReader(line.substring(line.indexOf(IOConstants.READER_INFO_SEPARATOR) + 1));
				if(cr != null) return cr;
			}
		}
		return getSimpleReader();
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
