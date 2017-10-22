package linus.io.config.configs;

public class MultipleBooleanConfig extends MultipleConfig<Boolean>{
	public MultipleBooleanConfig() {}

	private boolean[] values = new boolean[0];
	private Boolean[] complex = new Boolean[0];

	public MultipleBooleanConfig(String name, boolean... values){
		super(name);
		this.values = values;
		for(int i = 0; i < values.length; i++){
			complex[i] = Boolean.valueOf(values[i]);
		}
	}

	@Override
	public Boolean[] getValue() {
		return complex;
	}

	public boolean[] getPrimitiveValue(){
		return values;
	}

	@Override
	public Config<Boolean[]> read(String[] lines) {
		if(lines.length == 0){
			name = "";
			values = new boolean[0];
			complex = new Boolean[0];
			return this;
		}
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		values = new boolean[lines.length - 1];
		complex = new Boolean[lines.length - 1];
		for(int i = 1; i < lines.length; i++){
			values[i - 1] = Boolean.parseBoolean(lines[i].substring(lines[i].indexOf(VALUE_START) + 1, lines[i].length()).trim());
			complex[i - 1] = Boolean.valueOf(values[i - 1]);
		}
		return this;
	}

	@Override
	protected void setValue(Boolean[] value) {
		this.complex = value;
		for(int i = 0; i < values.length; i++){
			this.values[i] = value[i].booleanValue();
		}
	}

}
