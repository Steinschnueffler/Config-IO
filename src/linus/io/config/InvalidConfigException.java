package linus.io.config;

public class InvalidConfigException extends IllegalArgumentException{
	private static final long serialVersionUID = 1L;

	public InvalidConfigException(String str) {
		super(str);
	}

}
