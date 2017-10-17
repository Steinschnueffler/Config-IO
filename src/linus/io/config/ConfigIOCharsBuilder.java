package linus.io.config;

public class ConfigIOCharsBuilder implements ConfigIOChars{

	private char infoStart = '#';
	private char classStart = '@';
	private String classEnd = classStart + "finish";

	ConfigIOCharsBuilder() {}

	public ConfigIOCharsBuilder(char infoStart, char classStart, String classEnd) {
		setInfoStart(infoStart);
		setClassStart(classStart);
		setClassEnd(classEnd);
	}

	public ConfigIOCharsBuilder(ConfigIOChars chars){
		setInfoStart(chars.getInfoStart());
		setClassStart(chars.getClassStart());
		setClassEnd(chars.getClassEnd());
	}

	@Override
	public char getInfoStart() {
		return infoStart;
	}

	@Override
	public char getClassStart() {
		return classStart;
	}

	@Override
	public String getClassEnd() {
		return classEnd;
	}


	/**
	 *sets the comment start char to the given char
	 * @param infoStart
	 */
	public void setInfoStart(char infoStart){
		this.infoStart = infoStart;
	}

	/**
	 * sets the startClass char to the given char.
	 * This my will also change the ClassEnd
	 * @param classStart
	 */
	public void setClassStart(char classStart){
		classEnd.replace(this.classStart, classStart);
		this.classStart = classStart;
	}

	/**
	 * sets the classEnd String to the given String.
	 * the start of the given String may be changed because of the ClassStart char
	 * @param classEnd
	 */
	public void setClassEnd(String classEnd){
		if(!classEnd.startsWith("" +classStart))
			classEnd = new String("" +classStart).concat(classEnd);
		this.classEnd = classEnd;
	}

	public static ConfigIOCharsBuilder getDefault(){
		return new ConfigIOCharsBuilder();
	}

}
