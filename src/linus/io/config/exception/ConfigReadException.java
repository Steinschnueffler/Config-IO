package linus.io.config.exception;

import java.io.IOException;

public class ConfigReadException extends IOException {
	private static final long serialVersionUID = 1L;
	
	public ConfigReadException(Throwable cause) {
		super(cause);
	}
	
	public ConfigReadException() {
		super();
	}
}
