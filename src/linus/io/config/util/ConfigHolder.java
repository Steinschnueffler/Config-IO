package linus.io.config.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import linus.io.config.Config;

public class ConfigHolder implements Serializable, Cloneable, Iterable<Config<?>>{
	private static final long serialVersionUID = 1L;

	private ArrayList<Config<?>> list;
	private Hashtable<String, Integer> table;
	
	//Constructors
	
	public ConfigHolder() {
		this(16);
	}
	
	public ConfigHolder(Config<?>... configs) {
		this(16, configs);
	}
	
	public ConfigHolder(int initialCapacity) {
		list = new ArrayList<>(initialCapacity);
		table = new Hashtable<>(initialCapacity);
	}
	
	public ConfigHolder(int initialCapacity, Config<?>... configs) {
		this(initialCapacity);
		for(Config<?> cfg : configs)
			addConfig(cfg);
	}
	
	//list
	
	public void addConfig(Config<?> cfg) {
		table.put(cfg.getName(), list.size());
		list.add(cfg);
	}
	
	public Config<?> removeConfig(String name) {
		Integer index = table.get(name);
		if(index == null)
			return null;
		else
			table.remove(name);
		return list.remove(index.intValue());
	}
	
	public Config<?> removeConfig(Config<?> cfg) {
		String name = cfg.getName();
		if(table.containsKey(name)) return removeConfig(name);
		for(int i = 0; i < list.size(); i++) {
			Config<?> temp = list.get(i);
			if(temp.equals(cfg))
				return list.remove(i);
		}
		return null;
	}
	
	//standart collection
	
	public Config<?> getConfig(String name){
		Integer index = table.get(name);
		if(index == null) return null;
		return list.get(index);
	}
	
	public int getSize() {
		return list.size();
	}
	
	public boolean containsConfig(String name) {
		if(table.containsKey(name)) return true;
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getName().equals(name)) return true;
		}
		return false;
	}
	
	public boolean containsConfig(Config<?> cfg) {
		if(table.containsKey(cfg.getName())) return true;
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).equals(cfg)) return true;
		}
		return false;
	}
	
	//Override
	
	@Override
	public Iterator<Config<?>> iterator() {
		return list.iterator();
	}
	
	@Override
	public Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
