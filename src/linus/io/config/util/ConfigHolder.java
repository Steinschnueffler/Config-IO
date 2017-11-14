package linus.io.config.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import linus.io.config.Config;

public class ConfigHolder implements Serializable, Cloneable, Iterable<Config<?>>{
	private static final long serialVersionUID = 1L;

	private ArrayList<Config<?>> list = new ArrayList<>();
	private Hashtable<String, Integer> table = new Hashtable<>();
	private int index = 0;
	
	public ConfigHolder(Config<?>... configs) {
		for(Config<?> cfg : configs)
			addConfig(cfg);
	}
	
	public void addConfig(Config<?> cfg) {
		table.put(cfg.getName(), index);
		index++;
	}
	
	public Config<?> getConfig(String name){
		Integer index = table.get(name);
		if(index == null) return null;
		return list.get(index);
	}
	
	@Override
	public Iterator<Config<?>> iterator() {
		return list.iterator();
	}
	
	
}
