package genetic.csv;

public class TableFactory
{
	public static final String[] TOKENS = 
	{
		"ENROLLMENT"
	,	"CLASS"
	,	"DISTANCE"
	,	"CLASSROOM"
	};

	public static Table make(String token)
	{
		if(token.equals(TOKENS[0]))
			return new EnrollmentTable();

		if(token.equals(TOKENS[1]))
			return new ClassTable();	

		if(token.equals(TOKENS[2]))
			return DistanceTable.getInstance();
	
		if(token.equals(TOKENS[3]))
			return new ClassRoomTable();

		return null;
	}
}
