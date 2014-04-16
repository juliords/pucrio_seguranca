import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;


public class Main 
{	
	public static void main(String[] args)
			throws 	ClassNotFoundException,
					NoSuchAlgorithmException, 
					NoSuchPaddingException,
					UnsupportedEncodingException

	{		
		/*
		String pubPath = "../Keys/user03pub";
		byte[] pubRaw;
		try {
			pubRaw = FileHandler.readFile(pubPath);
			Security.loadPublicKey(pubRaw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		System.out.println(Common.binToHex(pubRaw));
		String salt = Common.genSalt();
		System.out.println(salt);
		System.out.println(Common.genPasswdHash("24681357", salt));
		*/
		
		while(true)
		{
			User user = Auth.step1();
			if(user == null)
			{
				System.out.println("Erro na autenticacao (etapa 1)");
				continue;
			}
			System.out.println(user.getName());
			
			int i;
			for(i = 0; i < 3; i++)
			{
				if(Auth.step2(user))
				{
					Auth.resetLoginTries(user.getLogin());
					break;
				}

				System.out.println("Senha errada, tente novamente!");
				Auth.increaseLoginTries(user.getLogin());
			}
			if(i == 3)
			{
				System.out.println("Você errou a senha 3 vezes. Espere 2 minutos e tente novamente!");
				continue;
			}
			
			for(i = 0; i < 3; i++)
			{
				if(Auth.step3(user))
				{
					Auth.resetLoginTries(user.getLogin());
					break;
				}

				System.out.println("Chave privada invalida, tente novamente!");
				Auth.increaseLoginTries(user.getLogin());
			}
			if(i == 3)
			{
				System.out.println("Você entrou com 3 chaves privadas invalidas. Espere 2 minutos e tente novamente!");
				continue;
			}
			
			System.out.println("Etapa 3 validada com sucesso!");
			//break; // continuar

			while(true)
			{
				System.out.print("Entre com o caminho do arquivo: ");
				String filepath;
				try {
					filepath = Common.readStdinLine();
				} catch (IOException e) {
					System.out.println("Erro ao abrir arquivo!");
					continue;
				}
				if(filepath.equals("quit")) break;
				saveFile(user, filepath);
			}
		}		
	}
	
	public static void saveFile(User user, String filename) throws NoSuchAlgorithmException, NoSuchPaddingException
	{
		
		byte[] indexPlain = FileHandler.readCryptedFile(filename, user.getPrivateKey(), user.getPublicKey());
		if(indexPlain == null)
		{
			//System.out.println("Erro ao decriptar arquivo!");
			return;
		}
		else
		{
			/*
			System.out.println("=========== BEGIN OF FILE ================");
			try 
			{
				System.out.println(new String(indexPlain, "UTF8"));
			} 
			catch (UnsupportedEncodingException e) 
			{
				System.out.println("NOTICE: arquivo a ser impresso nao eh UTF8!");
				System.out.println(Common.binToHex(indexPlain));
			}
			System.out.println("============ END OF FILE =================");
			
			return indexPlain;
			*/
			System.out.print("Entre com o nome do novo arquivo: ");
			String newFilePath;
			try {
				newFilePath = Common.readStdinLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			try {
				File f = new File(newFilePath);
				FileOutputStream out = new FileOutputStream(f);
				if(!f.exists()) f.createNewFile();
				
				out.write(indexPlain);
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
}
