package linus.io.config.io;

import java.io.Serializable;

public final class SerializableConfigData<E> implements Serializable{
	private static final long serialVersionUID = 1L;

	public final String name;
	public final E value;
	public final String classPath;

	public SerializableConfigData(String name, E value, String classPath) {
		this.name = name;
		this.value = value;
		this.classPath = classPath;
	}

}
