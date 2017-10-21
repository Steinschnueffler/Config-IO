package linus.io.config.configs;

public class SingleCharConfig extends SingleConfig<Character>{

	private char value = ' ';

	public SingleCharConfig(String name, char value) {
		super(name);
		this.value = value;
	}

	@Override
	public Character getValue() {
		return value;
	}

	public char getPrimitiveValue(){
		return value;
	}

	@Override
	public Config<Character> read(String[] lines) {
		name = lines[0].substring(0, lines[0].indexOf(SEPARATOR)).trim();
		value = lines[0].substring(lines[0].indexOf(SEPARATOR) + 1, lines[0].length()).charAt(0);
		return this;
	}

}
