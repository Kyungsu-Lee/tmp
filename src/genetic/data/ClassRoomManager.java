package genetic.data;

import java.util.ArrayList;
import java.util.HashMap;

public class ClassRoomManager
{
	private HashMap<String, ClassRoom> map;
	private static ClassRoomManager instance;

	private ClassRoomManager()
	{
		this.map = new HashMap<>();	
	}

	public static ClassRoomManager getInstance()
	{
		if(instance == null)
			instance = new ClassRoomManager();

		return instance;	
	}

	public void insertClassRoom(ClassRoom classRoom)
	{
		this.map.put(classRoom.getName(), classRoom);
	}

	public void insertClassRoom(String classRoomName, int capacity)
	{
		this.map.put(classRoomName, new ClassRoom(classRoomName, capacity));
	}

	public void clear()
	{
		map.clear();
	}

	public void enroll(ClassKey classKey, String classRoomName)
	{
		if(map.get(classRoomName) != null)
			map.get(classRoomName).enroll(classKey);
	}

	public ClassRoom getClassRoom(String classRoomName)
	{
		return this.map.get(classRoomName);
	}

	public ArrayList<ClassRoom> getAllClassRooms()
	{
		ArrayList<ClassRoom> tmp = new ArrayList<>();
		for(String key : map.keySet())
			tmp.add(map.get(key));
		return tmp;
	}

	public String toString()
	{
		StringBuilder tmp = new StringBuilder();
	
		for(String key : map.keySet())
			tmp.append(key + " : " + map.get(key).getNum() + "\n");

		return tmp.toString();
	}
}
