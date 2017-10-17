package linus.io.config;

import java.io.Closeable;

public abstract interface ConfigReader extends Closeable{

	public abstract <E extends ConfigBase> E next();

	public abstract boolean hasNext();

}
