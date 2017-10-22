package linus.io.config.configs;

public class MultipleLongConfig extends MultipleConfig<Long>{
	public MultipleLongConfig() {}

	private long[] values = new long[0];
	private Long[] complex = new Long[0];

	public MultipleLongConfig(String name, long... values) {
		super(name);
		this.values = values;
		for(int i = 0; i < complex.length; i++){
			complex[i] = Long.valueOf(values[i]);
		}
	}

	@Override
	public Long[] getValue() {
		return complex;
	}

	public long[] getPrimitiveValue(){
		return values;
	}

	@Override
	public Config<Long[]> read(String[] lines) {
		if(lines.length == 0){
			name = "";
			values = new long[0];
			complex = new Long[0];
			return this;
		}
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		values = new long[lines.length - 1];
		complex = new Long[lines.length - 1];
		for(int i = 1; i < lines.length; i++){
			values[i - 1] = Long.parseLong(lines[i].substring(lines[i].indexOf(VALUE_START) + 1, lines[i].length()).trim());
			complex[i - 1] = Long.valueOf(values[i - 1]);
		}
		return this;
	}

}
