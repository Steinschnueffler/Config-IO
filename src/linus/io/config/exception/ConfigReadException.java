package linus.io.config.exception;

public class ConfigReadException extends ConfigIOException {
	private static final long serialVersionUID = 1L;
	
	public ConfigReadException(Throwable cause) {
		super(cause);
	}
	
	public ConfigReadException() {
		super();
	}
}
