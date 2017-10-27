package linus.io.config;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Scanner;

import linus.io.config.configs.*;

/**
 *
 * @author Linus Dierheimer
 *
 */
public class SimpleConfigReader extends ConfigReader{

	private String lineBuffer;

	public SimpleConfigReader(String pathName) throws FileNotFoundException {
		this(new File(pathName));
	}

	public SimpleConfigReader(FileDescriptor fd) {
		this(new FileInputStream(fd));
	}

	public SimpleConfigReader(URI uri) throws FileNotFoundException {
		this(new File(uri));
	}

	public SimpleConfigReader(File f) throws FileNotFoundException {
		this(new FileInputStream(f));
	}

	public SimpleConfigReader(InputStream in) {
		super(in);
		reader = new Scanner(in);
		if(reader.hasNextLine())
			lineBuffer = reader.nextLine();
		buffer = nextConfig();
	}

	@Override
	protected Config<?> nextConfig(){
		if(lineBuffer == null) return null;

		String line = lineBuffer;
		//lehre Zeilen und comments überspringen
		while(line.trim().length() == 0 || line.startsWith("#")){
			//testen ob es überhaupt eine weitere Zeile gibt
			if(!reader.hasNextLine()) return null;
			line = reader.nextLine();
		}

		//wenn nach dem = was steht -> Single Config
		if(line.substring(line.indexOf(Config.SEPARATOR) + 1).trim().length() != 0){
			if(reader.hasNextLine())
				lineBuffer = reader.nextLine();
			else
				lineBuffer = null;
			return SingleConfig.getSingleConfig(line);
		}

		//ansonsten die Zeilen dazu auslesen
		ArrayList<String> lines = new ArrayList<>();
		lines.add(line);

		//testen Ob der reader noch was zum einlesen hat
		if(!reader.hasNextLine()){
			lineBuffer = null;
			return new SingleStringConfig(line.substring(0, line.indexOf(Config.SEPARATOR)), "");
		}

		//wenn ja dann alle dazugehöhrigen Zeilen lesen
		String data = reader.nextLine();
		while(!data.contains("" +Config.SEPARATOR) || data.startsWith("#") || data.trim().length() == 0){
			//testen auf lehre oder command Zeile
			if(!(data.trim().length() == 0 || data.trim().startsWith("#")))
				lines.add(data);
			//nächstes Element
			if(!reader.hasNextLine()){
				lineBuffer = null;
				return MultipleConfig.getMultipleConfig(lines.toArray(new String[lines.size()]));
			}
			data = reader.nextLine();
		}
		//data behinhalten ein =, daher gehört zu einer neue Config
		lineBuffer = data;

		//die aktuellen Zeilen als Config zurückgeben
		return MultipleConfig.getMultipleConfig(lines.toArray(new String[lines.size()]));
	}

}
