package linus.io.config.configs;

public class MultipleIntConfig extends MultipleConfig<Integer>{
	public MultipleIntConfig() {}

	private int[] values = new int[0];

	public MultipleIntConfig(String name, int... values){
		super(name);
		this.values = values;
	}

	@Override
	public Integer[] getValue() {
		Integer[] arr = new Integer[values.length];
		for(int i = 0; i < arr.length; i++){
			arr[i] = Integer.valueOf(values[i]);
		}
		return arr;
	}

	public int[] getPrimitiveValue(){
		return values;
	}

	@Override
	public Config<Integer[]> read(String[] lines) {
		if(lines.length == 0){
			name = "";
			values = new int[0];
			return this;
		}
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		values = new int[lines.length - 1];
		for(int i = 1; i < lines.length; i++){
			values[i - 1] = Integer.parseInt(lines[i].substring(lines[i].indexOf(VALUE_START) + 1, lines[i].length()).trim());
		}
		return this;
	}

}
