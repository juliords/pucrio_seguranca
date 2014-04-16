import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;

import javax.crypto.NoSuchPaddingException;


public class Main 
{
	private static User authStep1() throws ClassNotFoundException, NoSuchAlgorithmException 
	{
		System.out.print("Login: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String login;
		try {
			login = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		try 
		{
			return Auth.login(login);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args)
			throws 	ClassNotFoundException,
					NoSuchAlgorithmException, 
					NoSuchPaddingException,
					UnsupportedEncodingException

	{
		while(true)
		{
			User user = authStep1();
			if(user == null)
			{
				System.out.println("Erro na autenticacao (etapa 1)");
				continue;
			}
			System.out.println(user.getName());
		}
	}
}
