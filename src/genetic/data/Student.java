package genetic.data;

import java.util.ArrayList;
import java.util.HashMap;

import genetic.csv.*;

public class Student
{
	private String id;
	private String year;
	private String semester;
	private String class_code;
	private String class_section;

	public Student(String id, String year, String semester, String class_code, String class_section)
	{
		this.id = id;
		this.semester = semester;
		this.year = year;
		this.class_code = class_code;
		this.class_section = class_section;
	}

	public Student(Student other)
	{
		this.id = other.getId();
		this.semester = other.getSemester();
		this.year = other.getYear();
		this.class_code = other.getClassCode();
		this.class_section = other.getClassSection();
	}

	public String getId()
	{
		return this.id;
	}

	public String getYear() { return this.year; }
	public String getSemester() { return this.semester; }
	public String getClassCode() { return this.class_code; }
	public String getClassSection() { return this.class_section; }
	
	public ClassKey getClassKey()
	{
		return new ClassKey(year, semester, class_code, class_section);
	}

	@Override
	public int hashCode()
	{
		String tmp = id + year + semester + class_code + class_section;
		return tmp.hashCode();
	}

	@Override
	public String toString()
	{
		return "{"
		+ "id : " + id + ", "
		+ "year : " + year + ", "
		+ "semester : " + semester + ", "
		+ "class_code : " + class_code + ", "
		+ "class_section : " + class_section
		+ "}";
	}
}
