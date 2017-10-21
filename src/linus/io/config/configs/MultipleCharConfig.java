package linus.io.config.configs;

public class MultipleCharConfig extends MultipleConfig<Character>{
	public MultipleCharConfig() {}

	private char[] value = new char[0];
	private Character[] complex = new Character[0];

	public MultipleCharConfig(String name, char... values) {
		super(name);
		this.value = values;
		for(int i = 0; i < complex.length; i++){
			complex[i] = Character.valueOf(values[i]);
		}
	}

	@Override
	public Character[] getValue() {
		return complex;
	}

	public char[] getPrimitiveValue(){
		return value;
	}

	@Override
	public Config<Character[]> read(String[] lines) {
		if(lines.length == 0){
			name = "";
			value = new char[0];
			complex = new Character[0];
			return this;
		}
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		value = new char[lines.length - 1];
		complex = new Character[lines.length - 1];
		for(int i = 1; i < lines.length; i++){
			value[i - 1] = lines[i].substring(lines[i].indexOf(VALUE_START) + 1, lines[i].length()).trim().charAt(0);
			complex[i - 1] = Character.valueOf(value[i - 1]);
		}
		return this;
	}

}
