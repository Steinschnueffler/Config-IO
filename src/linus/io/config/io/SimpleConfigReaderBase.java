package linus.io.config.io;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import linus.io.config.Config;
import linus.io.config.MultipleConfig;
import linus.io.config.SingleConfig;
import linus.io.config.exception.ConfigReadException;

public class SimpleConfigReaderBase extends AbstractConfigReader{

	public SimpleConfigReaderBase(Reader in){
		super(in);
	}
	

	@Override
	protected Config<?> readNext() throws ConfigReadException {
		
		//ConfigBuffer auf Null falls es keine Zeilen gibt
		if(lineBuffer == null) return null;
		
		//Line Buffer auf die erste Zeile setzten, die zu einer Config gehöhrt.
		while(
			  lineBuffer.startsWith(IOConstants.COMMENT_START)||
			  lineBuffer.startsWith(IOConstants.READER_INFO_START)||
			  lineBuffer.trim().length() == 0
		) {
			try {
				lineBuffer = reader.readLine().trim();
			} catch (IOException e) {
				throw new ConfigReadException(e);
			}
			
			//Falls es keine Lines mehr gibt null zurückgeben
			if(lineBuffer == null) return null;
		}
		
		//Falls es einen Wert nach dem = gibt, dann eine SingleConfig zurückgeben
		if(lineBuffer.substring(lineBuffer.indexOf(Config.SEPARATOR) + 1).length() != 0) {
			try {
				lineBuffer = reader.readLine();
			} catch (IOException e) {
				throw new ConfigReadException(e);
			}
			return SingleConfig.getSingleConfig(lineBuffer);
		}
		
		//neue ArrayList anlegen, in der alle Werte der MultipleConfig gespeichert ist.
		//Erster Eintrag ist der Buffer, welcher der Name sein sollte.
		ArrayList<String> werte = new ArrayList<>();
		werte.add(lineBuffer);
		
		//alle Werte einlesen
		String aktuelleLine;
		do {
			try {
				aktuelleLine = reader.readLine();
			} catch (IOException e) {
				throw new ConfigReadException(e);
			}
			if(!aktuelleLine.startsWith(IOConstants.COMMENT_START)) werte.add(aktuelleLine);
		}while(!aktuelleLine.contains("" +Config.SEPARATOR));
		
		//da die Line zu einer neuen Config gehöhren zu scheint
		lineBuffer = aktuelleLine;
		
		return MultipleConfig.getMultipleConfig(werte.toArray(new String[werte.size()]));
	}

	@Override
	protected Config<?> firstConfig() throws ConfigReadException {
		
		//line Buffer auf die erste Zeile setzen 
		try {
			lineBuffer = reader.readLine();
		} catch (IOException e) {
			throw new ConfigReadException(e);
		}
		
		return nextConfig();
	}
}
