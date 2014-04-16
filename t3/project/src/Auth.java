import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;


public class Auth 
{
	public static void resetLoginTries(String login) throws ClassNotFoundException
	{
		try 
		{
			Connection conn = Database.getConnection();
			String sql = "UPDATE usuario SET erro_qtd=0, erro_hora=now() WHERE pk_login = ?;";
			PreparedStatement stm = conn.prepareStatement(sql);
			stm.setString(1, login);
			stm.execute();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void increaseLoginTries(String login) throws ClassNotFoundException
	{
		try 
		{
			Connection conn = Database.getConnection();
			String sql = "UPDATE usuario SET erro_qtd=erro_qtd+1, erro_hora=now() WHERE pk_login = ?;";
			PreparedStatement stm = conn.prepareStatement(sql);
			stm.setString(1, login);
			stm.execute();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static User login(String login) throws ClassNotFoundException, NoSuchAlgorithmException, SQLException
	{
		User user = User.getUser(login);
		if(user == null) return null;
		
		if(user.getLoginTries() >= 3 && Common.diffDate(new Date(), user.getLastLoginTry()) < 2)
		{
			System.out.println("Número máximo de tentativas estourado. Por favor, aguarde 2 minutos!");
			return null;
		}
		resetLoginTries(login);
		
		return user;
	}
}
