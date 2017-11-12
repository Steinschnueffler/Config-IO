package linus.io.config;

import linus.io.config.configs.SingleBooleanConfig;
import linus.io.config.configs.SingleCharConfig;
import linus.io.config.configs.SingleDoubleConfig;
import linus.io.config.configs.SingleIntConfig;
import linus.io.config.configs.SingleLongConfig;
import linus.io.config.configs.SingleStringConfig;
import linus.io.config.io.SerializableConfigData;

public abstract class SingleConfig<E> extends Config<E>{
	public SingleConfig() {}

	public SingleConfig(String name) {
		super(name);
	}

	public SingleConfig(String name, E value){
		super(name, value);
	}

	@Override
	public ConfigType getConfigType() {
		return ConfigType.Single;
	}

	@Override
	public String[] write() {
		String[] arr = {writeSimple()};
		return arr;
	}

	@Override
	public SingleConfig<E> clone() throws CloneNotSupportedException {
		return (SingleConfig<E>) super.clone();
	}

	@Override
	public SingleStringConfig toStringConfig() {
		String str = getValue() == null ? null : getValue().toString();
		return new SingleStringConfig(getName(), str);
	}

	public final String writeSimple(){
		return
			name+
			" "+
			SEPARATOR+
			" "+
			getValue().toString();
	}

	@Override
	public Config<E> read(SerializableConfigData<E> data) {
		this.name = data.name;
		this.value = data.value;
		return this;
	}
	
	@Override
	public Config<?> normalize() {
		return getSingleConfig(getName(), getValueAsString());
	}

	//static

	public static SingleConfig<?> getSingleConfig(String line){
		String name = line.substring(0, line.indexOf(Config.SEPARATOR)).trim();
		String value = line.substring(line.indexOf(Config.SEPARATOR) +1, line.length()).trim();

		return getSingleConfig(name, value);
	}
		
	public static SingleConfig<?> getSingleConfig(String name, String value) {
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
}
