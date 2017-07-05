package genetic.mutation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import genetic.data.*;
import genetic.csv.*;
import genetic.gene.*;

public class MutateClassAdaptor
{
	private ArrayList<ClassInfo> array;
	private ArrayList<ClassGene> classes;
	private ArrayList<ClassRoomGene> classRooms;
	
	public void set(ArrayList<ClassInfo> array)
	{
		this.array = array;
	}

	public ArrayList<ClassInfo> get()
	{
		HashMap<Integer, ClassInfo> map = new HashMap<>();
		Random rd = new Random();
		
		while((map.size() != array.size()))
		{
			int rd_class = rd.nextInt(array.size());

			if(map.containsKey(rd_class)) continue;

			map.put(rd_class, array.get(rd_class));
			while((array.get(rd_class).getClassRoom() == null))
			{
				int rd_num = rd.nextInt(ClassRoomManager.getInstance().getAllClassRooms().size());
//				System.out.println((array.size() - map.size()) + " : " + array.get(rd_num).getClassCode());
				if(
				   (ClassRoomManager.getInstance().getAllClassRooms().get(rd_num).hasTime(array.get(rd_class).getTime())) 
				 && !condition(array.get(rd_class), ClassRoomManager.getInstance().getAllClassRooms().get(rd_num))
				) continue;
			
				ClassRoomManager.getInstance().getAllClassRooms().get(rd_num).enroll(array.get(rd_class).getKey());
			}
			
		}

		return array;
	}

	public void setGene(Gene gene)
	{
		classRooms = gene.getClassRoomGene();
		classes = gene.getClassGene();
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
	
}
