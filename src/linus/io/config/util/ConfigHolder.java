package linus.io.config.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import linus.io.config.Config;

public class ConfigHolder extends ArrayList<Config<?>>{
	private static final long serialVersionUID = 1L;
	
	public ConfigHolder(int preSize) {
		super(preSize);
	}
	
	public ConfigHolder(Config<?>... configs) {
		super(Arrays.asList(configs));
	}
	
	public ConfigHolder(Collection<? extends Config<?>> configs) {
		super(configs);
	}
	
	public ConfigHolder() {
		super();
	}
	
}
