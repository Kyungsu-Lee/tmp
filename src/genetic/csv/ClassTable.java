package genetic.csv;

public class ClassTable extends Table
{
	public ClassTable()
	{
		
	}

	public Table selectRows(String attribute, String value)
	{
		Table table = TableFactory.make("CLASS");

		for(CSVElement row : rows)
		{
			if(row.getElement(attribute).equals(value))
				table.insertRow(row);
		}

		return table;
	}
}
