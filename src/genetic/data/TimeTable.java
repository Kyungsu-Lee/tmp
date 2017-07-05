package genetic.data;

import java.util.ArrayList;

import genetic.csv.*;

public class TimeTable
{
	private ArrayList<Integer> values = new ArrayList<>();

	public TimeTable()
	{
		
	}

	public void parse(String time)
	{
		String tmp = time.replaceAll("\"", "");
		String[] t = tmp.split(",");
		for(String s : t)
			this.setValue(s);
	}

	public void setValue(String str)
	{
		int value = 0;
		if(str.indexOf('M') >= 0)
			value += 10;
		if(str.indexOf("Tu") >= 0)
			value += 20;
		if(str.indexOf('W') >= 0)
			value += 30;
		if(str.indexOf("Th") >= 0)
			value += 40;
		if(str.indexOf('F') >= 0)
			value += 50;
		if(str.indexOf('S') >= 0)
			value += 60;

		try
		{
			int n = Integer.parseInt(str.charAt(str.length() - 1) + "");
			value += n;
		}catch(Exception e)
		{
			values.add(-1);
		}

		this.values.add(value);
	}

	public int[] getValues()
	{
		int[] tmp = new int[values.size()];
		for(int i=0; i<values.size(); i++)
		{
			tmp[i] = values.get(i);
		}
		return tmp;
	}

	public String[] getValue()
	{
		String[] str = new String[values.size()];
		
		for(int i=0; i < values.size(); i++)
		{
			str[i] = values.get(i).toString();
		}

		return str;
	}
}
