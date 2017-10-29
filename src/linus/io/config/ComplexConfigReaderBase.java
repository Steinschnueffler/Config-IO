package linus.io.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import linus.io.config.exception.ConfigReadException;

public class ComplexConfigReaderBase extends ConfigReader{

	public ComplexConfigReaderBase(InputStream source){
		super(source);
	}

	@Override
	protected Config<?> nextConfig() throws ReflectiveOperationException, ConfigReadException{
		//bis zur nächsten Config kommen
		String line;
		ArrayList<String> werte = new ArrayList<>();
		try {
			if(reader.ready()) return null;
			line = reader.readLine();
			while(!line.startsWith("" +chars.getClassStart())){
				if(reader.ready()) return null;
				line = reader.readLine();
			}

			//werte Einlesen
			if(reader.ready()) return null;
			String wert = reader.readLine();
			while(!wert.startsWith(chars.getClassEnd())){
				if(!wert.startsWith("" +chars.getInfoStart())) werte.add(wert);
				if(!reader.ready()) break;
				wert = reader.readLine();
			}
		}catch (IOException e) {
			throw new ConfigReadException(e);
		}
		
		//Config zurückgeben
		Class<?> clazz;
		Config<?> cfg;
		clazz = Class.forName(line.substring(line.indexOf(chars.getClassStart()) + 1));
		cfg = (Config<?>) clazz.getConstructor().newInstance();

		return cfg.read(werte.toArray(new String[werte.size()]));
	}	
}
