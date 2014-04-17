import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class Auth 
{
	/* --------------------------------------------------------------------- */
	
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
	
	/* --------------------------------------------------------------------- */
	
	public static User login(String login) throws ClassNotFoundException, NoSuchAlgorithmException, SQLException
	{
		User user = User.getUser(login);
		if(user == null) return null;
		
		if(user.getLoginTries() >= 3 && Common.diffDateMinutes(new Date(), user.getLastLoginTry()) < 2)
		{
			System.out.println("Número máximo de tentativas estourado. Por favor, aguarde 2 minutos!");
			return null;
		}
		resetLoginTries(login);
		
		return user;
	}
	
	/* --------------------------------------------------------------------- */
	
	public static User step1() throws ClassNotFoundException, NoSuchAlgorithmException 
	{
		System.out.print("Login: ");
		String login;
		try {
			login = Common.readStdinLine();
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
	
	public static boolean step2(User user) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		final int maxPasswdLen = 8;
		Integer[][] keysChosen = new Integer[maxPasswdLen][];

		for(int i = 0; i < maxPasswdLen; i++)
		{
			Integer[] keyboard = Common.randPermut(10);
			System.out.print("Teclado: ");
			for(int j = 0; j < 5; j++)
			{
				System.out.print(j+"->("+keyboard[2*j]+","+keyboard[(2*j)+1]+") / ");
			}
			System.out.println("");
			System.out.print("Escolha uma senha: ");
			Integer button;
			try {
				button = Integer.parseInt(Common.readStdinLine());
			} catch (NumberFormatException e) {
				button = -1;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
			if(button < 0 || button > 4)
			{
				System.out.println("Entrada inválida, tente novamente!");
				i--;
				continue;				
			}
			
			keysChosen[i] = new Integer[2];
			keysChosen[i][0] = keyboard[2*button];
			keysChosen[i][1] = keyboard[(2*button)+1];
		}
		
		for(int i = 0; i < Math.pow(2, maxPasswdLen); i++)
		{
			String passwd = "";
			for(int j = 0; j < maxPasswdLen; j++)
			{
				if((i & (1 << j)) > 0)
					passwd += keysChosen[j][0];
				else
					passwd += keysChosen[j][1];
			}
			String hash = Common.genPasswdHash(passwd, user.getPasswdSalt());
			if(hash.equals(user.getPasswdHash())) return true;
		}
		
		return false;
	}

	public static boolean step3(User user) throws NoSuchAlgorithmException, NoSuchPaddingException
	{
		System.out.println("Por favor, entre com o caminho da sua chave privada:");
		
		byte[] privRaw;
		try {
			String path = Common.readStdinLine();
			privRaw = FileHandler.readFile(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erro ao abrir arquivo!");
			return false;
		}
		
		System.out.print("Senha da chave privada: ");
		String passwd;
		try {
			passwd = Common.readStdinLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		PrivateKey privateKey;
		try 
		{
			privateKey = Security.loadPrivateKey(privRaw, passwd.getBytes("UTF8"));
		} 
		catch (InvalidKeyException | UnsupportedEncodingException e) 
		{
			System.out.println("ERROR: chave DES da chave privada invalida!");
			return false;
		} 
		catch (IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException e) 
		{
			System.out.println("ERROR: arquivo de chave privada invalido!");
			return false;
		} 
		
		user.setPrivateKey(privateKey);
		try 
		{
			return Security.checkKeyPair(privateKey, user.getPublicKey());
		} 
		catch (InvalidKeyException | SignatureException e) 
		{
			System.out.println("ERROR: chave publica e privada nao sao validas!");
			return false;
		} 
	}
}

