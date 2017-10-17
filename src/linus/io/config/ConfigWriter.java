package linus.io.config;

import java.io.Closeable;

public abstract interface ConfigWriter extends Closeable{

	public abstract void writeInfo(String info);

	public abstract void writeConfig(Config<?> cfg);

	public abstract void writeln();
}
