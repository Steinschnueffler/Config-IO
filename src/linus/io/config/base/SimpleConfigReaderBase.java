package linus.io.config.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import linus.io.config.configs.SingleStringConfig;
import linus.io.config.exception.ConfigReadException;

public class SimpleConfigReaderBase extends ConfigReader{

	private String lineBuffer;
	
	public SimpleConfigReaderBase(InputStream source) {
		super(source);
	}

	@Override
	protected Config<?> nextConfig() throws ConfigReadException{
		if(lineBuffer == null) return null;
		String line = lineBuffer;
		ArrayList<String> lines = new ArrayList<>();
		
		try {
			//lehre Zeilen und comments überspringen
			while(line.trim().length() == 0 || line.startsWith("#")){
				//testen ob es überhaupt eine weitere Zeile gibt
				if(reader.ready()) return null;
				line = reader.readLine();
			}

			//wenn nach dem = was steht -> Single Config
			if(line.substring(line.indexOf(Config.SEPARATOR) + 1).trim().length() != 0){
				if(!reader.ready())
					lineBuffer = reader.readLine();
				else
					lineBuffer = null;
				return SingleConfig.getSingleConfig(line);
			}

			//ansonsten die Zeilen dazu auslesen
			lines.add(line);

			//testen Ob der reader noch was zum einlesen hat
			if(reader.ready()){
				lineBuffer = null;
				return new SingleStringConfig(line.substring(0, line.indexOf(Config.SEPARATOR)), "");
			}

			//wenn ja dann alle dazugehöhrigen Zeilen lesen
			String data = reader.readLine();
			while(!data.contains("" +Config.SEPARATOR) || data.startsWith("#") || data.trim().length() == 0){
				//testen auf lehre oder command Zeile
				if(!(data.trim().length() == 0 || data.trim().startsWith("#")))
					lines.add(data);
				//nächstes Element
				if(reader.ready()){
					lineBuffer = null;
					return MultipleConfig.getMultipleConfig(lines.toArray(new String[lines.size()]));
				}
				data = reader.readLine();
			}
			
			//data behinhalten ein =, daher gehört zu einer neue Config
			lineBuffer = data;
		}catch(IOException e) {
			throw new ConfigReadException(e);
		}

		//die aktuellen Zeilen als Config zurückgeben
		return MultipleConfig.getMultipleConfig(lines.toArray(new String[lines.size()]));
	}

}
