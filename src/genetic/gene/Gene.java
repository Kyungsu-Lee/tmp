package genetic.gene;

import genetic.data.*;
import genetic.mutation.*;
import genetic.csv.*;

import java.util.ArrayList;

public class Gene
{
	private ArrayList<ClassGene> classes = new ArrayList<>();
	private ArrayList<ClassRoomGene> classRooms = new ArrayList<>();

	public Gene()
	{
		for(ClassRoom classRoom : ClassRoomManager.getInstance().getAllClassRooms())
			classRooms.add(new ClassRoomGene(classRoom.getName(), classRoom.getNum()));
	}

	public void addClass(ClassGene _class)
	{
		this.classes.add(_class);
		
		for(ClassRoomGene gene : classRooms)
			if(gene.getName().equals(_class.get_tmp_class_room()))
				gene.enroll(_class);
	}

	public ArrayList<ClassGene> getClassGene() { return this.classes; }
	public ArrayList<ClassRoomGene> getClassRoomGene() { return this.classRooms; }

	public int getTotalNextDistance()
	{
		int totalDistance = 0;

		setNextClass();

		for(ClassGene _class : classes)
			totalDistance += _class.getNextDistance(DistanceTable.getInstance());

		return totalDistance;
	}

	public void mutate(ArrayList<ClassGene> classes, ArrayList<ClassRoomGene> classRooms)
	{
		this.classes = classes;	
		this.classRooms = classRooms;
	}

	private void setNextClass()
	{
		for(ClassGene _class : classes)
		{
			_class.clear();
		}

		for(ClassGene _class : classes)
		{
			for(ClassGene other : classes)
				_class.setClassRelation(other);
		}
	}
}
