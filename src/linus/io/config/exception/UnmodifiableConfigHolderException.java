package linus.io.config.exception;

public class UnmodifiableConfigHolderException extends ConfigOperationException{
	private static final long serialVersionUID = 1L;

	public UnmodifiableConfigHolderException(String msg) {
		super(msg);
	}
	
	public UnmodifiableConfigHolderException(Throwable cause) {
		super(cause);
	}
	
	public UnmodifiableConfigHolderException(Throwable cause, String msg) {
		super(msg, cause);
	}
	
	public UnmodifiableConfigHolderException() {
		super();
	}

}

