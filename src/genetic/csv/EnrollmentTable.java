package genetic.csv;

public class EnrollmentTable extends Table
{
	public EnrollmentTable()
	{
		
	}

	public Table selectRows(String attribute, String value)
	{
		Table table = TableFactory.make("ENROLLMENT");

		for(CSVElement row : rows)
		{
			if(row.getElement(attribute).equals(value))
				table.insertRow(row);
		}

		return table;
	}
}
