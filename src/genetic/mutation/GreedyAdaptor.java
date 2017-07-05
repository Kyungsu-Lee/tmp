package genetic.mutation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import genetic.data.*;
import genetic.csv.*;
import genetic.gene.*;

public class GreedyAdaptor
{
	private ArrayList<ClassInfo> array;
	private ArrayList<ClassGene> classes;
	private ArrayList<ClassRoomGene> classRooms;

	public void set(ArrayList<ClassInfo> array)
	{
		this.array = array;

		Collections.sort(this.array, new MovementComp());
	}


	public ArrayList<ClassInfo> get(int rate)
	{
		if(rate < 0 || rate > 100) return null;
		double mutation_rate = (rate * array.size()-1) / 100.0;
		Random rd = new Random();

		for(int i=0; i<= mutation_rate; i++)
		{
			int rd_num = rd.nextInt(ClassRoomManager.getInstance().getAllClassRooms().size());
			if(
				(ClassRoomManager.getInstance().getAllClassRooms().get(rd_num).hasTime(array.get(i).getTime())) 
				&& !condition(array.get(i), ClassRoomManager.getInstance().getAllClassRooms().get(rd_num))
			  ) continue;

			ClassRoom _old = array.get(i).getClassRoom();
			ClassRoom _new = ClassRoomManager.getInstance().getAllClassRooms().get(rd_num);

			int _prev = array.get(i).getMovement();
			_new.enroll(array.get(i).getKey());
			int _next = array.get(i).getMovement();

			if(_prev < _next)
			{
				_old.enroll(array.get(i).getKey());
				continue;
			}		

		}

		return array;
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
		double mutation_rate = (rate * classes.size()-1) / 100.0;
		Random rd = new Random();

		Collections.sort(classes, new MovementGeneComp());

		for(int i=0; i<= mutation_rate; i++)
		{
			int rd_num = rd.nextInt(classRooms.size());
			if(
				(classRooms.get(rd_num).hasTime(classes.get(i).getTime())) 
				&& !condition(classes.get(i), classRooms.get(rd_num))
			  ) { continue;}

			ClassRoomGene _old = classes.get(i).getClassRoom();
			ClassRoomGene _new = classRooms.get(rd_num);

			int _prev = classes.get(i).getMovement();
			_new.enroll(classes.get(i));
			int _next = classes.get(i).getMovement();

			if(_prev < _next)
			{
				_old.enroll(classes.get(i));
				continue;
			}		
		}

		Gene gene = new Gene();
		gene.mutate(classes, classRooms);
		return gene;
	}

	private boolean condition(ClassInfo classInfo, ClassRoom classRoom)
	{
		boolean flag = true;

		try{
			flag &= Integer.parseInt(classInfo.getMaxNum()) <= classRoom.getNum();
			flag &= Integer.parseInt(classInfo.getTakingNum()) >= classRoom.getNum()/2;

		}catch(Exception e){
			return false;
		}
		return flag;
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

	private class MovementComp implements  Comparator<ClassInfo>
	{
		public int compare(ClassInfo arg1, ClassInfo arg0) //increase
		{
			Integer one = new Integer(arg0.getMovement());
			return one.compareTo(arg1.getMovement());
		}
	}

	private class MovementGeneComp implements  Comparator<ClassGene>
	{
		public int compare(ClassGene arg1, ClassGene arg0) //increase
		{
			Integer one = new Integer(arg0.getMovement());
			return one.compareTo(arg1.getMovement());
		}
	}
}
