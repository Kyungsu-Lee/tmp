package genetic.csv;

import java.io.*;
import genetic.data.TimeTable;

public class CSVReader
{
	private final String TOKEN = "::-::";
	private StringBuilder content;

	public CSVReader()
	{
		content = new StringBuilder("");
	}

	public CSVReader(String path, String fileName)
	{
		read(path, fileName);
	}

	public void read(String path, String fileName)
	{
		content = new StringBuilder("");

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(path+"/"+fileName));
			String line;

			while((line = br.readLine()) != null)
			{
				if(line == null || line.equals(""))
					continue;

				content.append(line + TOKEN);
			}
			br.close();
		}
		catch(Exception e)
		{
		}
	}

	/*
	*	for making table from csv file
	*/	
	public Table makeTable(String token)
	{
		if(content.toString().equals(""))
			return null;

		Table table = TableFactory.make(token);	
		String[] rows = content.toString().split(TOKEN);

		String[] attr = rows[0].split(",");
		
		for(int i=1; i < rows.length; i++)
		{
			String row = new String(rows[i]);
			String tmp = "";
	
			if(row.indexOf("\"") >= 0)
			{
				tmp = row.substring(row.indexOf("\"") + 1, row.lastIndexOf("\""));
				row = row.replace(tmp, "");
			}

			String[] value = row.split(",");
			if(!tmp.equals(""))
			{
				TimeTable tt = new TimeTable();
				tmp = tmp.replaceAll("\"", "");
				String[] times = tmp.split(",");
				for(String time : times) tt.setValue(time);
				String ttmp = "";
				for(String time : tt.getValue()) ttmp += time + "::";
				value[value.length-1] = ttmp.substring(0, ttmp.lastIndexOf("::"));
			}
			
			table.insertRow(new CSVElement(attr, value));
		}
		
		return table;
	}
}
