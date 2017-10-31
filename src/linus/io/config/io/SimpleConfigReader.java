package linus.io.config.io;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;

import linus.io.config.ConfigBase;
import linus.io.config.exception.ConfigReadException;

/**
 *
 * @author Linus Dierheimer
 *
 */
public class SimpleConfigReader extends SimpleConfigReaderBase{

	private Exception e = null;
	
	public Exception checkException() {
		return e;
	}
	
	public void supressException() {
		e = null;
	}
	
	public SimpleConfigReader(String pathName) throws FileNotFoundException {
		this(new File(pathName));
	}

	public SimpleConfigReader(FileDescriptor fd) {
		this(new FileInputStream(fd));
	}

	public SimpleConfigReader(URI uri) throws FileNotFoundException {
		this(new File(uri));
	}

	public SimpleConfigReader(File f) throws FileNotFoundException {
		this(new FileInputStream(f));
	}

	public SimpleConfigReader(InputStream in) {
		super(in);
	}
	
	@Override
	public void close(){
		try {
			super.close();
			e = null;
		} catch (ConfigReadException e) {
			this.e = e;
		}
	}
	
	@Override
	public <E extends ConfigBase> E next() {
		try {
			E temp = super.next();
			e = null;
			return temp;
		} catch (ConfigReadException | ReflectiveOperationException e) {
			this.e = e;
			return null;
		}
	}
	
}
