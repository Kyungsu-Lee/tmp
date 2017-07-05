package genetic.csv;

import java.util.*;

public abstract class Table
{
	protected ArrayList<CSVElement> rows = new ArrayList<>();

	public abstract Table selectRows(String attribute, String value);
	
	public void insertRow(CSVElement element)
	{
		rows.add(element);
	}

	public String toString()
	{
		StringBuilder tmp = new StringBuilder("");
		for(CSVElement element : rows)
			tmp.append(element.toString() + '\n');
		return tmp.toString();
	}

	public Object[] getElement(String attribute)
	{
		ArrayList<String> tmp = new ArrayList<>();

		for(CSVElement row : rows)
		{
			tmp.add(row.getElement(attribute));
		}

		return tmp.toArray();
	}

	public ArrayList<CSVElement> getRows()
	{
		return this.rows;
	}
}
