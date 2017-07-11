package genetic.gene;

import genetic.data.*;
import genetic.mutation.*;
import genetic.csv.*;

import java.util.ArrayList;

public class Gene
{
	private ArrayList<ClassGene> classes = new ArrayList<>();

	int distance;
	boolean distanceFlag = false;

	public Gene()
	{
		distanceFlag = false;
	}

	public void addClass(ClassGene _class)
	{
		this.classes.add(_class);
		
		for(ClassRoom room: ClassRoomManager.getInstance().getAllClassRooms())
		{
			ClassRoomGene gene = new ClassRoomGene(room.getName(), room.getNum());
			if(gene.getName().equals(_class.get_tmp_class_room()))
				gene.enroll(_class);
		}
	}

	public ArrayList<ClassGene> getClassGene() { return this.classes; }

	public int getTotalNextDistance()
	{
		if(distanceFlag) return this.distance;

		int totalDistance = 0;

		setNextClass();

		for(ClassGene _class : classes)
			totalDistance += _class.getNextDistance(DistanceTable.getInstance());

		distanceFlag = true;
		this.distance = totalDistance;

		return totalDistance;
	}

	public void mutate(ArrayList<ClassGene> classes)
	{
		this.classes = classes;
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
