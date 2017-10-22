package linus.io.config.configs;

public class MultipleStringConfig extends MultipleConfig<String>{
	public MultipleStringConfig() {}

	private String[] values = new String[0];

	public MultipleStringConfig(String name, String... values){
		super(name);
		this.values = values;
	}

	@Override
	public String[] getValue() {
		return values;
	}

	@Override
	public Config<String[]> read(String[] lines) {
		if(lines.length == 0){
			name = "";
			values = new String[0];
			return this;
		}
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		values = new String[lines.length - 1];
		for(int i = 1; i < lines.length; i++){
			values[i - 1] = lines[i].substring(lines[i].indexOf(VALUE_START) + 1, lines[i].length()).trim();
		}
		return this;
	}

	@Override
	protected void setValue(String[] value) {
		this.values = value;
	}

}
