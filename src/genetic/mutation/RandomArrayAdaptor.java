package genetic.mutation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import genetic.gene.*;
import genetic.data.*;

import genetic.data.ClassInfo;

public class RandomArrayAdaptor
{
	private ArrayList<ClassInfo> array;
	private ArrayList<ClassGene> classes;
	private ArrayList<ClassRoomGene> classRooms;
	
	public void set(ArrayList<ClassInfo> array)
	{
		this.array = array;
	}

	public ArrayList<ClassInfo> get(int rate)
	{
		if(rate < 0 || rate > 100) return null;

		double mutation_rate = (rate * array.size()) / 100.0;
		ArrayList<ClassInfo> tmp = new ArrayList<>();
		HashMap<Integer, ClassInfo> map = new HashMap<>();
		
		while(!(map.size() >= mutation_rate))
		{
			Random rd = new Random();
			int random_num = rd.nextInt(array.size());
			if(array.get(random_num).getClassRoom() == null) continue;
			map.put(random_num, array.get(random_num));
//			System.out.println("added : " + array.get(random_num));
		}

		for(int i : map.keySet())
			tmp.add(map.get(i));

		return tmp;
	}

	
	public void setGene(Gene gene)
	{
		classes = new ArrayList<ClassGene>();
		for(ClassGene _class : gene.getClassGene())
			classes.add(new ClassGene(_class));

		for(ClassGene _class : classes)
		{
			_class.clear();
		}

		for(ClassGene _class : classes)
		{
			for(ClassGene other : classes)
				_class.setClassRelation(other);
		}

		classRooms = new ArrayList<>();
		for(ClassRoom classRoom : ClassRoomManager.getInstance().getAllClassRooms())
			classRooms.add(new ClassRoomGene(classRoom.getName(), classRoom.getNum()));

		for(ClassGene _class : classes)
		for(ClassRoomGene room : classRooms)
			if(room.getName().equals(_class.get_tmp_class_room()))
				room.enroll(_class);
	}


	public Gene mutate(int rate)
	{
		if(rate < 0 || rate > 100) return null;

		double mutation_rate = (rate * classes.size()) / 100.0;
		ArrayList<ClassGene> tmp = new ArrayList<>();
		HashMap<Integer, ClassGene> map = new HashMap<>();
		
		Random rd = new Random();
		while(!(map.size() >= mutation_rate))
		{
			int random_num = rd.nextInt(classes.size());
			if(classes.get(random_num).getClassRoom() == null) continue;
			map.put(random_num, classes.get(random_num));
		}

		for(int i : map.keySet())
			tmp.add(map.get(i));

		for(int i=0; i< tmp.size(); i++)
		{
			int rd_num = rd.nextInt(classRooms.size());
			if(
				(classRooms.get(rd_num).hasTime(tmp.get(i).getTime())) 
				&& !condition(tmp.get(i), classRooms.get(rd_num))
			  ) { continue;}

			ClassRoomGene _new = classRooms.get(rd_num);

			_new.enroll(tmp.get(i));
		}


		Gene gene = new Gene();
		gene.mutate(classes, classRooms);
		return gene;
	}

	private boolean condition(ClassGene classInfo, ClassRoomGene classRoom)
	{
		boolean flag = true;

		try{
			flag &= Integer.parseInt(classInfo.getMaxNum()) <= classRoom.getNum();
			flag &= Integer.parseInt(classInfo.getNum()) >= classRoom.getNum()/2;

		}catch(Exception e){
			return false;
		}
		return flag;
	}
}
