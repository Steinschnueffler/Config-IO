package linus.io.config.io;

public class IOConstants {

	private IOConstants() {}
	
	public static final char READER_INFO_START = '%';
	public static final char READER_INFO_SEPARATOR = '=';
	public static final String FITTING_READER_INFO =
			READER_INFO_START+
		    "FittingReader "+
			READER_INFO_SEPARATOR;
	public static final char CLASS_PATH_START = '@';
	public static final String CLASS_PATH_END =
			CLASS_PATH_START+
			"Finish";

}
