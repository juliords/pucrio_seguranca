import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class Main 
{	
	public static void main(String[] args)
			throws 	ClassNotFoundException,
					NoSuchAlgorithmException, 
					NoSuchPaddingException,
					UnsupportedEncodingException

	{
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
					break;

				System.out.println("Senha errada, tente novamente!");
				Auth.increaseLoginTries(user.getLogin());
			}
			if(i == 3)
			{
				System.out.println("Você errou a senha 3 vezes. Espere 2 minutos e tente novamente!");
				continue;
			}
			System.out.println("Etapa 2 validada com sucesso!");
			
			break;
		}
		
		testDecrypt();
	}
	
	public static void testDecrypt() throws NoSuchAlgorithmException, NoSuchPaddingException
	{
		String privKeyPath = "../sample/userpriv";
		String pubKeyPath = "../sample/userpub";
		byte[] privKeyRaw, pubKeyRaw;
		try 
		{
			privKeyRaw = FileHandler.readFile(privKeyPath);
			pubKeyRaw = FileHandler.readFile(pubKeyPath);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return;
		}

		/* ---------------------------------------------------------------------- */

		PrivateKey privateKey;
		PublicKey publicKey;
		try 
		{
			privateKey = Security.loadPrivateKey(privKeyRaw, "segredo".getBytes("UTF8"));
			publicKey = Security.loadPublicKey(pubKeyRaw);
		} 
		catch (InvalidKeyException | UnsupportedEncodingException e) 
		{
			System.out.println("ERROR: chave DES da private key invalida!");
			return;
		} 
		catch (IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException e) 
		{
			System.out.println("ERROR: arquivo(s) de chave RSA invalido(s)!");
			return;
		} 

		/* ---------------------------------------------------------------------- */
		
		boolean keyPairCheck = false;
		try 
		{
			keyPairCheck = Security.checkKeyPair(privateKey, publicKey);
		} 
		catch (InvalidKeyException | SignatureException e) 
		{
			System.out.println("ERROR: chave publica e privada nao sao validas!");
			keyPairCheck = false;
		} 
		System.out.println("Key check: "+(keyPairCheck ? "True" : "False"));
		
		/* ---------------------------------------------------------------------- */
		
		byte[] indexPlain = FileHandler.readCryptedFile("../sample/XXXXYYYYZZZZ", privateKey, publicKey);
		if(indexPlain == null)
		{
			System.out.println("Erro ao decriptar arquivo!");
		}
		else
		{
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
		}
	}
}
