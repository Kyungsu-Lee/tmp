package genetic.data;

import java.util.HashMap;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;
import genetic.csv.DistanceTable;
import genetic.gene.*;

public class ClassManager
{
	HashMap<ClassKey, ClassInfo> map;

	private static ClassManager instance;
	DistanceTable table;

	private ClassManager()
	{
		map = new HashMap<ClassKey, ClassInfo>();
	}

	public static ClassManager getInstance()
	{
		if(instance == null)
			instance = new ClassManager();
		
		return instance;
	}

	public void setDistanceTable(DistanceTable table)
	{
		this.table = table;
	}

	public void insertClass(ClassInfo classInfo)
	{
		if(!classInfo.getClassRoomName().equals("."))
		map.put(classInfo.getKey(), classInfo);
	}

	public ClassInfo get(ClassKey key)
	{
		return map.get(key);
	}
	
	public void clear()
	{
		map.clear();
	}

	public Gene makeGene()
	{
		Gene gene = new Gene();
		for(ClassKey key : map.keySet())
			gene.addClass(map.get(key).makeGene());
		return gene;
	}

	public ArrayList<ClassInfo> getAllClasses()
	{
		ArrayList<ClassInfo> tmp = new ArrayList<ClassInfo>();

		for(ClassKey key : map.keySet())
			tmp.add(map.get(key));
		
		return tmp;
	}


	public ArrayList<ClassInfo> getAllClasses(Comparator comp)
	{
		ArrayList<ClassInfo> tmp = new ArrayList<>();

		for(ClassKey key : map.keySet())
			tmp.add(map.get(key));

		Collections.sort(tmp, comp);

		return tmp;
	}

	public void addStudent(ClassKey key, Student student)
	{
		if(!map.containsKey(key))
			return;
		get(key).addStudent(student);
	}

	public ArrayList<ClassKey> getClassKeys()
	{
		ArrayList<ClassKey> tmp = new ArrayList<>();
		for(ClassKey key : map.keySet())
			tmp.add(key);
		
		return tmp;
	}

	public void setNextClass()
	{
		for(ClassKey key : map.keySet())
		{
			map.get(key).clearNextClass();
			map.get(key).clearPreviousClass();
		}

		for(ClassKey key : map.keySet())
		{
			for(ClassKey p : map.keySet())
			{
				map.get(key).setNextClass(map.get(p));
			}
		}			
	}

	public String getNextClass()
	{
		String tmp = "";
		for(ClassKey key : map.keySet())
		{
			tmp += map.get(key).getClassCode() + " => ";
			for(ClassInfo c : map.get(key).getNextClass())
				tmp += c.getClassCode() + ", ";
			tmp += "\n";
		}
		return tmp;
	}

	public String getNextClassDistance(DistanceTable table)
	{
		String tmp = "";
		for(ClassKey key : map.keySet())
		{
			tmp += map.get(key).getClassCode() + " => ";
			tmp += map.get(key).getNextDistance(table);
			tmp += "\n";
		}
		return tmp;
	}

	public String getNextClassDistance()
	{
		return getNextClassDistance(this.table);
	}

	public int getNextTotalDistance(DistanceTable table)
	{
		int tmp = 0;
		for(ClassKey key : map.keySet())
		{
			tmp += map.get(key).getNextDistance(table);
		}
		return tmp;
	}

	public int getPreviousClassDistance(DistanceTable table)
	{
		int tmp = 0;

		for(ClassKey key : map.keySet())
			tmp += map.get(key).getPreviousDistance(table);
		return tmp;
	}

	public int getPreviousClassDistance()
	{
		return getPreviousClassDistance(this.table);
	}

	public int getNextTotalDistance()
	{
		return getNextTotalDistance(table);
	}

	public String ClassPair()
	{
		ArrayList<String> arr = new ArrayList<>();
		StringBuilder tmp = new StringBuilder();

		for(ClassKey key : map.keySet())
			arr.add(map.get(key).getClassCode() + "(" + map.get(key).getClassSection() + ")" + "=>" + map.get(key).getClassRoomName() + "\n");

		Collections.sort(arr);

		for(String str : arr)
			tmp.append(str);

		return tmp.toString();
	}

	@Override
	public String toString()
	{
		StringBuilder tmp = new StringBuilder("");
		for(ClassKey key : map.keySet())
			tmp.append(map.get(key).toString() + "\n");
		return tmp.toString();
	}
}
