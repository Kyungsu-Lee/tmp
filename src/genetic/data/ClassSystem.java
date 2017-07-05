package genetic.data;

import java.util.HashSet;
import java.util.Iterator;
import genetic.csv.*;

public class ClassSystem
{
	Table student;
	Table _class;
	Table classRoom;
	DistanceTable distanceTable;

	public ClassSystem(String data)
	{
		student = new CSVReader(data, "enrollment.csv").makeTable("ENROLLMENT");
		_class  = new CSVReader(data, "class.csv").makeTable("CLASS");
		classRoom = new CSVReader(data, "classroom.csv").makeTable("CLASSROOM");
		distanceTable = (DistanceTable)(new CSVReader(data, "distance.csv").makeTable("DISTANCE"));

		ClassManager.getInstance().setDistanceTable(distanceTable);
	}

	public void selectSemester(int year, int semester)
	{
		Table sub_stu = student.selectRows("year", Integer.toString(year)).selectRows("semester", Integer.toString(semester));
		Table sub_class = _class.selectRows("year", Integer.toString(year)).selectRows("semester", Integer.toString(semester));


		for(CSVElement e : sub_class.getRows())
		{
			ClassManager.getInstance().insertClass(new ClassInfo(
				e.getElement("year"),
				e.getElement("semester"),
				e.getElement("class_code"),
				e.getElement("class_section"),
				e.getElement("credit"),
				e.getElement("max_num"),
				e.getElement("class_room"),
				e.getElement("taking_num"),
				e.getElement("class_time")
			));
		}

		for(CSVElement e : sub_stu.getRows())
		{
			StudentManager.getInstance().insertStudent(new Student(
				e.getElement("ID"),
				e.getElement("year"),
				e.getElement("semester"),
				e.getElement("class_code"),
				e.getElement("class_section")
			));
		}

		for(CSVElement e : classRoom.getRows())
		{
			for(Object o : e.getAttributes())
			{
				ClassRoomManager.getInstance().insertClassRoom(new ClassRoom(
					o.toString(),
					e.getElement(o.toString())
				));
			}
		}

		for(Student s : StudentManager.getInstance().getStudentList())
		{
			ClassManager.getInstance().addStudent(s.getClassKey(), s);
		}
	
		for(ClassKey key : ClassManager.getInstance().getClassKeys())
		{
//			System.out.println(key + " : " + ClassManager.getInstance().get(key).getClassRoom());
			ClassRoomManager.getInstance().enroll(
				key,
				ClassManager.getInstance().get(key).getClassRoomName()
			);
		}
	}
}
