package linus.io.config.util;

import java.io.Serializable;

import linus.io.config.Config;

public class ConfigData<E> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public final String name;
	public final E value;
	public final String[] comment;
	public final String classPath;
	
	public ConfigData(String name, E value, String classPath, String... comment) {
		this.name = name;
		this.value = value;
		this.comment = comment;
		this.classPath = classPath;
	}
	
	public ConfigData(Config<E> cfg) {
		this.name = cfg.getName();
		this.value = cfg.getValue();
		this.comment = cfg.getComments();
		this.classPath = cfg.getClass().getName();
	}

	public Config<E> toConfig(){
		return new Config<E>() {}.read(this);
	}
}
