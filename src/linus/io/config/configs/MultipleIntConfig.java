package linus.io.config.configs;

public class MultipleIntConfig extends MultipleConfig<Integer>{
	public MultipleIntConfig() {}

	private int[] values = new int[0];
	private Integer[] complex = new Integer[0];

	public MultipleIntConfig(String name, int... values){
		super(name);
		this.values = values;
		for(int i = 0; i < complex.length; i++){
			complex[i] = Integer.valueOf(values[i]);
		}
	}

	@Override
	public Integer[] getValue() {
		return complex;
	}

	public int[] getPrimitiveValue(){
		return values;
	}

	@Override
	public Config<Integer[]> read(String[] lines) {
		if(lines.length == 0){
			name = "";
			values = new int[0];
			complex = new Integer[0];
			return this;
		}
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		values = new int[lines.length - 1];
		complex = new Integer[lines.length - 1];
		for(int i = 1; i < lines.length; i++){
			values[i - 1] = Integer.parseInt(lines[i].substring(lines[i].indexOf(VALUE_START) + 1, lines[i].length()).trim());
			complex[i - 1] = Integer.valueOf(values[i - 1]);
		}
		return this;
	}

	@Override
	protected void setValue(Integer[] value) {
		this.complex = value;
		for(int i = 0; i < value.length; i++){
			this.values[i] = value[i].intValue();
		}
	}

}
