package linus.io.config;

public class InvalidConfigWriterException extends IllegalArgumentException{
	private static final long serialVersionUID = 1L;

	public InvalidConfigWriterException(String str) {
		super(str);
	}

}
