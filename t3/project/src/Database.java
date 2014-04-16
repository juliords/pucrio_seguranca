import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Database {

	public static Connection getConnection() 
			throws ClassNotFoundException, SQLException
	{
		Class.forName("org.postgresql.Driver");
		return DriverManager
		        .getConnection("jdbc:postgresql://localhost:5432/seguranca",
		                "seguranca", "seguranca");
	}
}
