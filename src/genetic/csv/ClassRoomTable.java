package genetic.csv;

public class ClassRoomTable extends Table
{
	public ClassRoomTable()
	{
		
	}

	public Table selectRows(String attribute, String value)
	{
		Table table = TableFactory.make("CLASSROOM");

		for(CSVElement row : rows)
		{
			if(row.getElement(attribute).equals(value))
				table.insertRow(row);
		}

		return table;
	}
}
