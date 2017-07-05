package genetic.mutation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import genetic.data.*;
import genetic.csv.*;
import genetic.gene.*;

public class MutationAdaptor
{
	private ArrayList<ClassInfo> array1;
	private ArrayList<ClassInfo> array2;

	public void set(ArrayList<ClassInfo> array1, ArrayList<ClassInfo> array2)
	{
		this.array1 = array1;
		this.array2 = array2;
	}

	//rate for array1
	public ArrayList<ClassInfo> get(int rate)
	{
		if(rate < 0 || rate > 100) return null;
		double mutation_rate = (rate * array1.size()-1) / 100.0;
		Random rd = new Random();

		HashSet<ClassInfo> tmp = new HashSet<>();

		while(tmp.size() <= mutation_rate)
		{
			int rd_num = rd.nextInt();
			tmp.add(array1.get(rd_num));
		}
	
		assert array1.size() == array2.size();

		for(int i=0; i<array2.size()-1 ; i++)
		{
			if(!tmp.contains(array2.get(i))) tmp.add(array2.get(i));
		}


		ArrayList<ClassInfo> array = new ArrayList<>();
		for(ClassInfo classInfo : tmp)
			array.add(classInfo);

		return array;
	}

	public Gene mutate(Gene gene1, Gene gene2, int rate)
	{
		ArrayList<ClassGene> class1 = new ArrayList<>();
		ArrayList<ClassRoomGene> room1 = new ArrayList<>();
		ArrayList<ClassGene> class2 = new ArrayList<>();
		ArrayList<ClassRoomGene> room2 = new ArrayList<>();

		for(ClassGene _class : gene1.getClassGene())
			class1.add(new ClassGene(_class));

		for(ClassGene _class : class1)
		{
			_class.clear();
		}

		for(ClassGene _class : class1)
		{
			for(ClassGene other : class1)
				_class.setClassRelation(other);
		}

		for(ClassRoom classRoom : ClassRoomManager.getInstance().getAllClassRooms())
			room1.add(new ClassRoomGene(classRoom.getName(), classRoom.getNum()));

		for(ClassGene _class : class1)
		for(ClassRoomGene room : room1)
			if(room.getName().equals(_class.get_tmp_class_room()))
				room.enroll(_class);

		for(ClassGene _class : gene2.getClassGene())
			class2.add(new ClassGene(_class));

		for(ClassGene _class : class2)
		{
			_class.clear();
		}

		for(ClassGene _class : class2)
		{
			for(ClassGene other : class2)
				_class.setClassRelation(other);
		}

		for(ClassRoom classRoom : ClassRoomManager.getInstance().getAllClassRooms())
			room2.add(new ClassRoomGene(classRoom.getName(), classRoom.getNum()));

		for(ClassGene _class : class2)
		for(ClassRoomGene room : room2)
			if(room.getName().equals(_class.get_tmp_class_room()))
				room.enroll(_class);


		if(rate < 0 || rate > 100) return null;
		double mutation_rate = (rate * class1.size()-1) / 100.0;
		Random rd = new Random();

		HashSet<ClassGene> tmp = new HashSet<>();

		while(tmp.size() <= mutation_rate)
		{
			int rd_num = rd.nextInt(class1.size());
			tmp.add(class1.get(rd_num));
		}
		for(int i=0; i<class2.size()-1 ; i++)
		{
			if(!tmp.contains(class2.get(i))) 
			{
				tmp.add(class2.get(i));
			}
		}


		ArrayList<ClassGene> array = new ArrayList<>();
		ArrayList<ClassRoomGene> array2 = new ArrayList<>();
		for(ClassGene classInfo : tmp)
		{
			array.add(classInfo);
			array2.add(classInfo.getClassRoom());
		}
		
		Gene gene = new Gene();
		gene.mutate(array, array2);
		return gene;
	}
}
