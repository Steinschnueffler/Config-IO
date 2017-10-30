package linus.io.config.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import linus.io.config.base.Config;
import linus.io.config.exception.ConfigOperationException;

public class ConfigHolder implements Iterable<Config<?>>, Cloneable{

	private ArrayList<Config<?>> configs = new ArrayList<>();
	
	public ConfigHolder(Config<?>... configs) {
		add(configs);
	}
	
	public void add(Config<?>... configs) {
		this.configs.addAll(Arrays.asList(configs));
	}
	
	public void remove(Config<?> cfg) {
		this.configs.remove(cfg);
	}
	
	public void remove(int index) {
		this.configs.remove(index);
	}
	
	public int size() {
		return this.configs.size();
	}
	
	public Config<?> get(int index){
		return this.configs.get(index);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Config<?>> toArrayList(){
		return (ArrayList<Config<?>>) configs.clone();
	}
	
	@Override
	public String toString() {
		return configs.toString();
	}
	
	@Override
	public Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new ConfigOperationException(e);
		}
	}
	
	@Override
	public Iterator<Config<?>> iterator() {
		return configs.iterator();
	}
	
}
