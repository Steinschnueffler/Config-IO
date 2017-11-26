package linus.io.config.exception;

public class ConfigWriteEexception extends ConfigIOException{
	private static final long serialVersionUID = 1L;
	
	public ConfigWriteEexception(Throwable cause) {
		super(cause);
	}
	
	public ConfigWriteEexception() {
		super();
	}
}
