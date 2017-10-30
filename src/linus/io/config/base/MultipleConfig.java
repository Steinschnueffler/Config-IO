package linus.io.config.base;

import java.util.Arrays;
import java.util.Iterator;

import linus.io.config.configs.MultipleBooleanConfig;
import linus.io.config.configs.MultipleDoubleConfig;
import linus.io.config.configs.MultipleIntConfig;
import linus.io.config.configs.MultipleLongConfig;
import linus.io.config.configs.MultipleStringConfig;
import linus.io.config.io.SerializableConfigData;

public abstract class MultipleConfig<E> extends Config<E[]> implements Iterable<E>{
	public MultipleConfig() {}

	public static final char VALUE_START = '-';

	protected E[] value = null;

	@SafeVarargs
	public MultipleConfig(String name, E... value){
		super(name);
		this.value = value;
	}

	@Override
	public ConfigType getConfigType() {
		return ConfigType.Multiple;
	}

	@Override
	public String[] write() {
		return writeSimple();
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {

			private int zeiger = 0;
			private E[] vals = getValue();


			@Override
			public boolean hasNext() {
				return zeiger < vals.length;
			}

			@Override
			public E next() {
				return vals[zeiger++];
			}
		};
	}
	
	@Override
	public Config<?> normalize() {
		//bad performance :(
		return getMultipleConfig(writeSimple());
	}

	@Override
	public String toString() {
		return getName() + " " + SEPARATOR + " " + Arrays.deepToString(getValue());
	}

	@Override
	public MultipleConfig<E> clone() throws CloneNotSupportedException {
		return (MultipleConfig<E>) super.clone();
	}

	@Override
	public E[] getValue() {
		return value;
	}

	@Override
	public MultipleConfig<E> read(SerializableConfigData<E[]> data) {
		this.name = data.name;
		this.value = data.value;
		return this;
	}

	@Override
	public MultipleStringConfig toStringConfig() {
		String[] vals = new String[value.length];
		for(int i = 0; i < vals.length; i++){
			vals[i] = getValue() == null ? null : getValue().toString();
		}
		return new MultipleStringConfig(getName(), vals);
	}

	public final String[] writeSimple(){
		String[] lines = new String[getValue().length + 1];
		lines[0] = name +" " +SEPARATOR;
		for(int i = 0; i < getValue().length; i++){
			lines[i + 1] = " " +VALUE_START +" " +getValue()[i];
		}
		return lines;
	}

	//static

	public static MultipleConfig<?> getMultipleConfig(String[] lines){
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
}
