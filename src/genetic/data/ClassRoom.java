package genetic.data;

import java.util.ArrayList;

public class ClassRoom
{
	private int capacity;
	private String name;

	private ArrayList<ClassKey> classes;

	public ClassRoom(String name, int capacity)
	{
		this.name = name;
		this.capacity = capacity;
		this.classes = new ArrayList<>();
	}

	public ClassRoom(String name, String capacity)
	{
		this.name = name;
		try{
		this.capacity = Integer.parseInt(capacity);
		}catch(Exception e){}
		this.classes = new ArrayList<>();
	}

	public String getName() { return this.name; }
	public int getNum()	{ return this.capacity; }

	public void enroll(ClassKey classKey)
	{
		this.classes.add(classKey);
		ClassManager.getInstance().get(classKey).setClassRoom(this);
	}

	public void makeEmpty(ClassKey classKey)
	{
		classes.remove(classKey);
	}
	
	public boolean hasTime(int[] time)
	{
		boolean flag = false;

		for(ClassKey key : classes)
			for(int i=0; i<time.length; i++)
			{
				if(time[i] == -1) return false;
				for(int j=0; j< ClassManager.getInstance().get(key).getTime().length; j++)
					flag |= (time[i] == ClassManager.getInstance().get(key).getTime()[j]);
			}

		return flag;
			
	}

	@Override
	public int hashCode()	{ return this.name.hashCode(); }

	@Override
	public String toString()
	{
		StringBuilder tmp = new StringBuilder();
		
		tmp.append("{\n");
		tmp.append(name + " : " + capacity + "\n");
		tmp.append("classes\n");
		for(ClassKey key : classes)
			tmp.append("\t" + key + "\n");
		tmp.append("}");
		return tmp.toString();
	}
}
