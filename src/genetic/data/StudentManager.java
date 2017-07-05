package genetic.data;

import java.util.ArrayList;

public class StudentManager
{
	ArrayList<Student> students;

	public static StudentManager instance;

	private StudentManager()
	{
		students = new ArrayList<>();
	}

	public static StudentManager getInstance()
	{
		if(instance == null)
			instance = new StudentManager();

		return instance;
	}

	public void clear()
	{
		students.clear();
	}

	public void insertStudent(Student stuInfo)
	{
		students.add(stuInfo);
	}

	public ArrayList<Student> getStudentList()
	{
		return this.students;
	}

	@Override
	public String toString()
	{
		StringBuilder tmp = new StringBuilder();
		for(Student student : students)
			tmp.append(student + "\n");
		return tmp.toString();
	}
}
