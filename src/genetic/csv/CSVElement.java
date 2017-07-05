package genetic.csv;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;

public class CSVElement
{
	private Map<String, String> info = new HashMap<>();

	public CSVElement(String[] attribute, String[] value)
	{
		insert(attribute, value);
	}

	public Object[] getAttributes()
	{
		ArrayList<String> tmp = new ArrayList<>();

		for(String key : info.keySet())
			tmp.add(key);
	
		return tmp.toArray();
	}

	public String getElement(String attribute)
	{
		return this.info.get(attribute);
	}
	
	public boolean hasAttribute(String attribute)
	{
		return this.info.containsKey(attribute);
	}

	public void insert(String attribute, String value)
	{
		this.info.put(attribute, value);
	}

	public void insert(String[] attribute, String[] value)
	{
		if(attribute.length != value.length)
		{
			return;
		}

		for(int i=0; i<attribute.length; i++)
			insert(attribute[i], value[i]);
	}

	public String toString()
	{
		String tmp = "";
		Set info = this.info.keySet();
		for(Iterator iter = info.iterator(); iter.hasNext();)
		{
			String key = (String)iter.next();
			String value = this.info.get(key);

			tmp += "{" + key + " : " + value + "}";
		}

		return tmp;
	}
}
