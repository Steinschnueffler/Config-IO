package linus.io.config;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import linus.io.config.configs.*;

class ReflectiveLoader {

	private ReflectiveLoader() {}

	public static ConfigIOChars loadConfigIOChars(String classPath) throws ReflectiveOperationException{
		Class<?> clazz = Class.forName(classPath);
		return (ConfigIOChars) clazz.newInstance();
	}

	public static Config<?> loadConfigComplex(String classPath, String[] readed) throws ReflectiveOperationException{

		if(classPath.startsWith("linus.io.config.configs")){
			if(classPath.equals("linus.io.config.configs.SingleStringConfig"))
				return new SingleStringConfig().read(readed);
			if(classPath.equals("linus.io.config.configs.MultipleStringConfig"))
				return new MultipleStringConfig().read(readed);
			if(classPath.equals("linus.io.config.configs.SingleIntConfig"))
				return new SingleIntConfig().read(readed);
			if(classPath.equals("linus.io.config.configs.MultipleIntConfig"))
				return new MultipleIntConfig().read(readed);
		}

		Class<?> clazz = Class.forName(classPath);
		Config<?> cfg = (Config<?>) clazz.newInstance();
		return cfg.read(readed);
	}

	public static Config<?> loadConfigSimple(String[] readed){
		if(readed.length == 0) return null;
		if(readed.length == 1) return loadSingleConfig(readed[0]);
		return loadMultipleConfig(readed);
	}

	private static SingleConfig<?> loadSingleConfig(String readed){
		String name = readed.substring(0, readed.indexOf(Config.SEPARATOR)).trim();
		String value = readed.substring(readed.indexOf(Config.SEPARATOR) + 1, readed.length()).trim();

		return new SingleStringConfig(name, value);
	}

	private static MultipleConfig<?> loadMultipleConfig(String[] readed){
		if(readed.length == 0) return null;
		String name = readed[0].substring(0, readed[0].indexOf(Config.SEPARATOR)).trim();
		String[] vals = new String[readed.length - 1];
		for(int i = 1; i < readed.length; i++){
			vals[i - 1] = readed[i].substring(readed[i].indexOf(MultipleConfig.VALUE_START) + 1, readed[i].length()).trim();
		}
		return new MultipleStringConfig(name, vals);
	}

	public static ConfigReader loadConfigReader(String classPath, String pathName) throws FileNotFoundException, ReflectiveOperationException{
		return loadConfigReader(classPath, new File(pathName));
	}

	public static ConfigReader loadConfigReader(String classPath, File source) throws FileNotFoundException, ReflectiveOperationException{
		return loadConfigReader(classPath, new FileInputStream(source));
	}

	public static ConfigReader loadConfigReader(String classPath, FileDescriptor fd) throws ReflectiveOperationException{
		return loadConfigReader(classPath, new FileInputStream(fd));
	}

	public static ConfigReader loadConfigReader(String classPath, InputStream source) throws ReflectiveOperationException{
		if(classPath.startsWith("linus.io.config")){
			if(classPath.equals("linus.io.config.SimpleConfigReader"))
				return new SimpleConfigReader(source);
			if(classPath.equals("linus.io.config.ComplexConfigReader"))
				return new ComplexConfigReader(source);
		}

		Class<?> clazz = Class.forName(classPath);
		return (ConfigReader) clazz.newInstance();
	}
}
