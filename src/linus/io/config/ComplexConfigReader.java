package linus.io.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ComplexConfigReader extends ConfigReader{

	public ComplexConfigReader(File f) throws FileNotFoundException {
		this(new FileInputStream(f));
	}

	public ComplexConfigReader(InputStream source) {
		super(source);
	}

	@Override
	protected Config<?> nextConfig() {

		//bis zur nächsten Config kommen
		if(!reader.hasNextLine()) return null;
		String line = reader.nextLine();
		while(!line.startsWith("" +chars.getClassStart())){
			if(!reader.hasNextLine()) return null;
			line = reader.nextLine();
		}

		//werte Einlesen
		ArrayList<String> werte = new ArrayList<>();
		if(!reader.hasNextLine()) return null;
		String wert = reader.nextLine();
		while(!wert.startsWith(chars.getClassEnd())){
			if(!wert.startsWith("" +chars.getInfoStart())) werte.add(wert);
			if(!reader.hasNextLine()) break;
			wert = reader.nextLine();
		}

		//Config zurückgeben
		Class<?> clazz;
		Config<?> cfg;
		try {
			clazz = Class.forName(line.substring(line.indexOf(chars.getClassStart()) + 1));
			cfg = (Config<?>) clazz.newInstance();
		} catch (ReflectiveOperationException e) {
			return nextConfig();
		}

		return cfg.read(werte.toArray(new String[werte.size()]));

	}

}