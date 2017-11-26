package linus.io.config.exception;

public class ConfigIOException extends Exception{
	private static final long serialVersionUID = 1L;

	public ConfigIOException(String msg) {
		super(msg);
	}
	
	public ConfigIOException(Throwable cause) {
		super(cause);
	}
	
	public ConfigIOException(Throwable cause, String msg) {
		super(msg, cause);
	}
	
	public ConfigIOException() {
		super();
	}
}
