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
	{
		String privKeyPath = "../sample/userpriv";
		String pubKeyPath = "../sample/userpub";
		byte[] privKeyRaw, pubKeyRaw;
		try 
		{
			privKeyRaw = Common.readFile(privKeyPath);
			pubKeyRaw = Common.readFile(pubKeyPath);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return;
		}
		
		PrivateKey privateKey;
		PublicKey publicKey;
		try 
		{
			privateKey = Security.loadPrivateKey(privKeyRaw, "segredo".getBytes("UTF8"));
			publicKey = Security.loadPublicKey(pubKeyRaw);
		} 
		catch (InvalidKeyException | UnsupportedEncodingException e) 
		{
			System.out.println("ERROR: chave DES da private key inválida!");
			return;
		} 
		catch (IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException e) 
		{
			System.out.println("ERROR: arquivo(s) de chave RSA inválido(s)!");
			return;
		} 
		catch (NoSuchAlgorithmException | NoSuchPaddingException e) 
		{
			e.printStackTrace();
			return;
		} 
		
		boolean keyPairCheck = false;
		try 
		{
			keyPairCheck = Security.checkKeyPair(privateKey, publicKey);
		} 
		catch (InvalidKeyException | SignatureException e) 
		{
			System.out.println("ERROR: chave pública e privada não são válidas!");
			keyPairCheck = false;
		} 
		catch (NoSuchAlgorithmException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("Key check: "+(keyPairCheck ? "True" : "False"));
		
		String indexPath = "../sample/index";
		byte[] indexRaw, indexEnvRaw, indexAsdRaw;
		try 
		{
			indexRaw = Common.readFile(indexPath+".env");
			indexEnvRaw = Common.readFile(indexPath+".enc");
			indexAsdRaw = Common.readFile(indexPath+".asd");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return;
		}

		byte[] indexKeyPlain;
		try 
		{
			indexKeyPlain = Security.asymmetricDecryption(indexRaw, privateKey);
		} 
		catch (InvalidKeyException e) 
		{
			System.out.println("ERROR: chave privada do envelope digital inválida!");
			return;
		} 
		catch (IllegalBlockSizeException | BadPaddingException e) 
		{
			System.out.println("ERROR: envelope digital inválido!");
			return;
		}
		catch (NoSuchAlgorithmException | NoSuchPaddingException e) 
		{
			e.printStackTrace();
			return;
		} 
		
		byte[] indexPlain;
		try 
		{
			indexPlain = Security.symmetricDecryption(indexEnvRaw, indexKeyPlain);
		} 
		catch (InvalidKeyException e) 
		{
			System.out.println("ERROR: chave do arquivo criptografado inválida!");
			return;
		} 
		catch (IllegalBlockSizeException | BadPaddingException e) 
		{
			System.out.println("ERROR: arquivo criptografado corrompido!");
			return;
		}
		catch (NoSuchAlgorithmException | NoSuchPaddingException e) 
		{
			e.printStackTrace();
			return;
		} 

		System.out.println("=========== BEGIN OF FILE ================");
		try 
		{
			System.out.println(new String(indexPlain, "UTF8"));
		} 
		catch (UnsupportedEncodingException e) 
		{
			System.out.println("ERROR: arquivo a ser impresso não é UTF8!");
		}
		System.out.println("=============END OF FILE =================");
		
		boolean indexSignCheck = false;
		try 
		{
			indexSignCheck = Security.checkSignature(publicKey, indexPlain, indexAsdRaw);
		} 
		catch (InvalidKeyException e) 
		{
			System.out.println("ERROR: chave pública da verificação da assinatura inválida!");
		} 
		catch (SignatureException e) 
		{
			System.out.println("ERROR: não foi possível verificar assinatura!");
		}
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
			return;
		} 
	    System.out.println("Digest check: "+(indexSignCheck ? "True" : "False"));
		
	}
}
