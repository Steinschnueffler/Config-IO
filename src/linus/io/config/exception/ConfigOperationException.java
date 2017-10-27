package linus.io.config.exception;

public class ConfigOperationException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ConfigOperationException() {
		super();
	}

	public ConfigOperationException(String str) {
		super(str);
	}

	public ConfigOperationException(Throwable cause){
		super(cause);
	}

	public ConfigOperationException(String str, Throwable cause) {
		super(str, cause);
	}
}
