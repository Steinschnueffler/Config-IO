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
public class SimpleConfigReader implements ConfigReader{

	private final Scanner reader;
	private Config<?> buffer;
	private String lineBuffer;
	private InputStream source;

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
		reader = new Scanner(in);
		source = in;
		if(reader.hasNextLine())
			lineBuffer = reader.nextLine();
		buffer = nextConfig();
	}

	private Config<?> nextConfig(){
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
			return getSingleConfig(line);
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
				return getMultipleConfig(lines.toArray(new String[lines.size()]));
			}
			data = reader.nextLine();
		}
		//data behinhalten ein =, daher gehört zu einer neue Config
		lineBuffer = data;

		//die aktuellen Zeilen als Config zurückgeben
		return getMultipleConfig(lines.toArray(new String[lines.size()]));
	}

	private SingleConfig<?> getSingleConfig(String line){
		String name = line.substring(0, line.indexOf(Config.SEPARATOR)).trim();
		String value = line.substring(line.indexOf(Config.SEPARATOR) +1, line.length()).trim();

		//testen was es am ehesten für eine Config ist
		try{
			return new SingleIntConfig(name, Integer.parseInt(value));
		}catch(Exception e){}
		try{
			return new SingleDoubleConfig(name, Double.parseDouble(value));
		}catch(Exception e){}
		try{
			return new SingleLongConfig(name, Long.parseLong(value));
		}catch (Exception e) {}

		if(value.equalsIgnoreCase("false"))
			return new SingleBooleanConfig(name, false);
		if(value.equalsIgnoreCase("true"))
			return new SingleBooleanConfig(name, true);

		//ansonsten char bzw String Config zurückgeben
		if(value.length() == 1) return new SingleCharConfig(name, value.charAt(0));
		return new SingleStringConfig(name, value);
	}

	private MultipleConfig<?> getMultipleConfig(String[] lines){
		String name = lines[0].substring(0, lines[0].indexOf(Config.SEPARATOR)).trim();
		if(lines.length == 1) return new MultipleStringConfig(name, new String[0]);

		//testen was es am ehesten für eine Config ist
		try{
			int[] data = new int[lines.length - 1];
			for(int i = 0; i < data.length; i++){
				String str = lines[i + 1].substring(lines[i + 1].indexOf(MultipleConfig.VALUE_START) + 1).trim();
				data[i] = Integer.parseInt(str);
			}
			return new MultipleIntConfig(name, data);
		}catch(Exception e){}
		try{
			boolean[] data = new boolean[lines.length - 1];
			for(int i = 0; i < data.length; i++){
				String str = lines[i + 1].substring(lines[i + 1].indexOf(MultipleConfig.VALUE_START) + 1).trim();
				if(str.equalsIgnoreCase("false"))
					data[i] = false;
				else if(str.equalsIgnoreCase("true"))
					data[i] = true;
				else throw new Exception();
			}
			return new MultipleBooleanConfig(name, data);
		}catch(Exception e){}
		try{
			double[] data = new double[lines.length - 1];
			for(int i = 0; i < data.length; i++){
				String str = lines[i + 1].substring(lines[i + 1].indexOf(MultipleConfig.VALUE_START) + 1).trim();
				data[i] = Double.parseDouble(str);
			}
			return new MultipleDoubleConfig(name, data);
		}catch(Exception e){}
		try{
			long[] data = new long[lines.length - 1];
			for(int i = 0; i < data.length; i++){
				String str = lines[i + 1].substring(lines[i + 1].indexOf(MultipleConfig.VALUE_START) + 1).trim();
				data[i] = Long.parseLong(str);
			}
			return new MultipleLongConfig(name, data);
		}catch (Exception e) {}
		try{
			char[] data = new char[lines.length - 1];
			for(int i = 0; i < data.length; i++){
				String str = lines[i + 1].substring(lines[i + 1].indexOf(MultipleConfig.VALUE_START) + 1).trim();
				if(str.length() > 1) throw new Exception();
				data[i] = lines[i + 1].charAt(0);
			}
		}catch(Exception e){}
		String[] data = new String[lines.length - 1];
		for(int i = 0; i < data.length; i++){
			data[i] = lines[i + 1].substring(lines[i + 1].indexOf(MultipleConfig.VALUE_START) + 1).trim();
		}
		return new MultipleStringConfig(name, data);
	}

	@Override
	public boolean hasNext(){
		return buffer != null;
	}

	@Override
	public void close(){
		reader.close();
	}

	@Override
	public InputStream getSource() {
		return source;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends ConfigBase> E next() {
		Config<?> cfg = buffer;
		buffer = nextConfig();
		return (E) cfg;
	}

}
