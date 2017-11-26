package linus.io.config.exception;

public class UnmodifiableConfigException extends ConfigOperationException{
	private static final long serialVersionUID = 1L;

	public UnmodifiableConfigException(String msg) {
		super(msg);
	}
	
	public UnmodifiableConfigException(Throwable cause) {
		super(cause);
	}
	
	public UnmodifiableConfigException(Throwable cause, String msg) {
		super(msg, cause);
	}
	
	public UnmodifiableConfigException() {
		super();
	}

}
