package genetic.gene;

import java.util.ArrayList;
import genetic.data.*;

public class ClassRoomGene
{
	private ArrayList<ClassGene> classes = new ArrayList<>();
	private String className;
	private int num;

	public ClassRoomGene(String className, int num)
	{
		this.className = className;
		this.num = num;
	}

	public String getName() { return this.className; }	
	public int getNum() { return this.num; }
	public void setNum(int num) { this.num = num; }

	public void enroll(ClassGene _class)
	{
		this.classes.add(_class);
		_class.setClassRoom(this);
	}

	public boolean hasTime(int[] time)
	{
		boolean flag = false;

		for(ClassGene _class : classes)
			for(int i=0; i<time.length; i++)
			{
				if(time[i] == -1) return false;
				for(int j=0; j< _class.getTime().length; j++)
					flag |= (time[i] == _class.getTime()[j]);
			}

		return flag;
	}

	
}
