package linus.io.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ComplexConfigReaderBase extends ConfigReader{

	public ComplexConfigReaderBase(InputStream source){
		super(source);
	}

	@Override
	protected Config<?> nextConfig() throws ReflectiveOperationException, IOException{
		//bis zur nächsten Config kommen
				if(reader.ready()) return null;
				String line = reader.readLine();
				while(!line.startsWith("" +chars.getClassStart())){
					if(reader.ready()) return null;
					line = reader.readLine();
				}

				//werte Einlesen
				ArrayList<String> werte = new ArrayList<>();
				if(reader.ready()) return null;
				String wert = reader.readLine();
				while(!wert.startsWith(chars.getClassEnd())){
					if(!wert.startsWith("" +chars.getInfoStart())) werte.add(wert);
					if(!reader.ready()) break;
					wert = reader.readLine();
				}

				//Config zurückgeben
				Class<?> clazz;
				Config<?> cfg;
				clazz = Class.forName(line.substring(line.indexOf(chars.getClassStart()) + 1));
				cfg = (Config<?>) clazz.getConstructor().newInstance();

				return cfg.read(werte.toArray(new String[werte.size()]));
	}	
}
