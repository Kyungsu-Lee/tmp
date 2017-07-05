package genetic.data;

public class ClassKey
{
	private String semester;
	private String year;
	private String class_code;
	private String class_section;

	public ClassKey(String year, String semester, String class_code, String class_section)
	{
		this.year = year;
		this.semester = semester;
		this.class_code = class_code;
		this.class_section = class_section;
	}

	public String getYear() { return this.year; }
	public String getSemester() { return this.semester; }
	public String getClassCode() { return this.class_code; }
	public String getClassSection() { return this.class_section; }

	@Override
	public int hashCode()
	{
		String tmp = year + semester + class_code + class_section;
		return tmp.hashCode();
	}

	@Override
	public String toString()
	{
		String tmp = "";
		tmp += "{";
		tmp += "year : " + year;
		tmp += "\t semester : " + semester;
		tmp += "\t class_code : " + class_code;
		tmp += "\t class_section : " + class_section;
		tmp += "";
		return tmp;
	}

	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof ClassKey))
			return false;

		ClassKey key = (ClassKey)o;

		return this.year.equals(key.getYear())
		&&	this.semester.equals(key.getSemester())
		&&	this.class_code.equals(key.getClassCode())
		&&	this.class_section.equals(key.getClassSection())
		;
	}
}
