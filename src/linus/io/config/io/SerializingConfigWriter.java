package linus.io.config.io;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;

import linus.io.config.Config;
import linus.io.config.exception.ConfigWriteEexception;

public class SerializingConfigWriter extends SerializingConfigWriterBase{

	private Exception e = null;
	
	public Exception checkException() {
		return e;
	}
	
	public void supressException() {
		e = null;
	}
	
	public SerializingConfigWriter(String pathName) throws FileNotFoundException {
		this(new File(pathName));
	}

	public SerializingConfigWriter(URI uri) throws FileNotFoundException {
		this(new File(uri));
	}

	public SerializingConfigWriter(FileDescriptor fd) {
		this(new FileOutputStream(fd));
	}

	public SerializingConfigWriter(File f) throws FileNotFoundException {
		this(new FileOutputStream(f));
	}

	public SerializingConfigWriter(OutputStream os) {
		super(os);
	}

	public SerializingConfigWriter(OutputStream os, ConfigIOChars chars) {
		this(os);
		setConfigIOChars(chars);
	}
	
	@Override
	public void close(){
		try {
			super.close();
			e = null;
		} catch (ConfigWriteEexception e) {
			this.e = e;
		}
	}
	
	@Override
	public void setConfigIOChars(ConfigIOChars chars){
		try {
			super.setConfigIOChars(chars);
			e = null;
		} catch (ConfigWriteEexception e) {
			this.e = e;
		}
	}
	
	@Override
	public void flush(){
		try {
			super.flush();
			e = null;
		} catch (ConfigWriteEexception e) {
			this.e = e;
		}
	}
	
	@Override
	public void writeConfig(Config<?> cfg){
		try {
			super.writeConfig(cfg);
			e = null;
		} catch (ConfigWriteEexception e) {
			this.e = e;
		}
	}
	
	@Override
	public void writeHeader(){
		try {
			super.writeHeader();
			e = null;
		} catch (ConfigWriteEexception e) {
			this.e = e;
		}
	}
	
	@Override
	public void writeInfo(String info){
		try {
			super.writeInfo(info);
			e = null;
		} catch (ConfigWriteEexception e) {
			this.e = e;
		}
	}
	
	@Override
	public void writeln(){
		try {
			super.writeln();
			e = null;
		} catch (ConfigWriteEexception e) {
			this.e = e;
		}
	}
	
	@Override
	public Appendable append(char c){
		try {
			super.append(c);
			e = null;
		} catch (ConfigWriteEexception e) {
			this.e = e;
		}
		return this;
	}
	
	@Override
	public Appendable append(CharSequence csq){
		try {
			super.append(csq);
			e = null;
		} catch (ConfigWriteEexception e) {
			this.e = e;
		}
		return this;
	}
	
	@Override
	public Appendable append(CharSequence csq, int start, int end){
		try {
			super.append(csq, start, end);
			e = null;
		} catch (ConfigWriteEexception e) {
			this.e = e;
		}
		return this;
	}

}
