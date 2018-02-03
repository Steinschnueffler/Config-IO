package linus.io.config.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import linus.io.config.Config;
import linus.io.config.exception.ConfigReadException;
import linus.io.config.exception.UnmodifiableConfigHolderException;
import linus.io.config.io.AbstractConfigReader;

public class ConfigHolder implements Serializable, Cloneable, Iterable<Config<?>>{
	private static final long serialVersionUID = 1L;

	public static final ConfigHolder EMPTY_HOLDER = new ConfigHolder(0);
	
	private ArrayList<Config<?>> list;
	private HashMap<String, Integer> table;
	
	private final boolean modifiable;
	
	//Constructors
	
	public ConfigHolder(Config<?>... configs) {
		this(16, configs);
	}
	
	public ConfigHolder(int initialCapacity, Config<?>... configs) {
		this(initialCapacity, false, configs);
	}
	
	public ConfigHolder(int initialCapacity, boolean modifiable, Config<?>... configs) {
		this.modifiable = modifiable;
		list = new ArrayList<>(initialCapacity);
		table = new HashMap<>(initialCapacity);
		for(Config<?> cfg : configs)
			addConfig(cfg);
	}
	
	private ConfigHolder(HashMap<String, Integer> map, ArrayList<Config<?>> list, boolean modifiable) {
		this.table = map;
		this.list = list;
		this.modifiable = modifiable;
	}
	
	//Config Operations
	
	@SuppressWarnings("unchecked")
	public <E> E getValue(String name) {
		Config<?> cfg = getConfig(name);
		return cfg == null ? null : (E) cfg.getValue();
	}
	
	//list
	
	public void addConfig(Config<?> cfg) {
		checkModifiable();
		table.put(cfg.getName(), list.size());
		list.add(cfg);
	}
	
	public Config<?> removeConfig(String name) {
		checkModifiable();
		Integer index = table.get(name);
		if(index == null)
			return null;
		else
			table.remove(name);
		return list.remove(index.intValue());
	}
	
	public Config<?> removeConfig(Config<?> cfg) {
		checkModifiable();
		String name = cfg.getName();
		if(table.containsKey(name)) return removeConfig(name);
		for(int i = 0; i < list.size(); i++) {
			Config<?> temp = list.get(i);
			if(temp.equals(cfg))
				return list.remove(i);
		}
		return null;
	}
	
	public Config<?> removeConfig(int index){
		checkModifiable();
		return removeConfig(list.get(index));
	}
	
	private void checkModifiable() {
		if(!modifiable) throw new UnmodifiableConfigHolderException();
	}
	
	//standart collection
	
	public Config<?> getConfig(String name){
		Integer index = table.get(name);
		return index == null ? null : list.get(index.intValue());
	}
	
	public Config<?> getConfig(int index){
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
	
	public Config<?>[] toArray(){
		return (Config<?>[]) list.toArray();
	}
	
	public ConfigHolder sort(Comparator<Config<?>> comp) {
		if(!modifiable) return modifiable().sort(comp);
		list.sort(comp);
		table.clear();
		for(int i = 0; i < list.size(); i++) {
			Config<?> cfg = list.get(i);
			table.put(cfg.getName(),  i);
		}
		return this;
	}
	
	//Override
	
	public ConfigHolder modifiable() {
		return new ConfigHolder(table, list, true);
	}

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

	@Override
	public String toString() {
		return list.toString();
	}

	//static
	
	public static ConfigHolder loadFromeFile(String pathName) throws ConfigReadException {
		return loadFromPath(Paths.get(pathName));
	}
	
	public static ConfigHolder loadFromFile(File f) throws ConfigReadException {
		return loadFromPath(f.toPath());
	}
	
	public static ConfigHolder loadFromPath(Path path) throws ConfigReadException {
		ConfigFile cf = new ConfigFile(path);
		try {
			cf.createNewFile();
			return loadFromReader(cf.getFittingReader());
		} catch (IOException e) {
			throw new ConfigReadException(e);
		}
	}
	
	public static ConfigHolder loadFromReader(AbstractConfigReader reader) throws ConfigReadException {
		ConfigHolder ch = new ConfigHolder();
		while(reader.hasNext()){
			ch.addConfig(reader.nextConfig());
		}
		return ch;
	}

	private static final ConfigHolder systemPropertys = loadSystemPropertys();
	
	private static ConfigHolder loadSystemPropertys() {
		Map<String, String> map = System.getenv();
		Properties props = System.getProperties();
		ConfigHolder ch = new ConfigHolder(props.size() + map.size());
		for(Entry<String, String> entry : map.entrySet())
			ch.addConfig(new Config<String>(entry.getKey(), entry.getValue()));
		for(Entry<Object, Object> entry : props.entrySet())
			ch.addConfig(new Config<Object>(entry.getKey().toString(), entry.getValue()));
			
		return ch;
	}
	
	public static ConfigHolder systemPropertys() {
		return systemPropertys;
	}
}
