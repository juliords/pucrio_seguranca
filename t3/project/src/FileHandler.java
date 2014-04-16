import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class FileHandler 
{
	public static byte[] readFile(String filename) throws IOException
	{
		File f = new File(filename);
        
		FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);

        byte[] keyBytes = new byte[(int) f.length()];
        
        dis.readFully(keyBytes);
        
        dis.close();
        fis.close();
        return keyBytes;
	}
	
	public static byte[] readCryptedFile(String filename, PrivateKey priv, PublicKey pub)
			throws NoSuchAlgorithmException, NoSuchPaddingException
	{
		String filePath = filename;
		byte[] fileRaw, fileEnvRaw, fileAsdRaw;
		try 
		{
			fileRaw = readFile(filePath+".enc");
			fileEnvRaw = readFile(filePath+".env");
			fileAsdRaw = readFile(filePath+".asd");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return null; // Erro na leitura do arquivos arquivos
		}

		/* ---------------------------------------------------------------------- */

		byte[] fileKeyPlain;
		try 
		{
			fileKeyPlain = Security.asymmetricDecryption(fileEnvRaw, priv);
		} 
		catch (InvalidKeyException e) 
		{
			//System.out.println("ERROR: chave privada do envelope digital invalida!");
			return null;
		} 
		catch (IllegalBlockSizeException | BadPaddingException e) 
		{
			//System.out.println("ERROR: envelope digital invalido!");
			return null;
		}

		/* ---------------------------------------------------------------------- */
		
		byte[] filePlain;
		try 
		{
			filePlain = Security.symmetricDecryption(fileRaw, fileKeyPlain);
		} 
		catch (InvalidKeyException e) 
		{
			//System.out.println("ERROR: chave do arquivo criptografado invalida!");
			return null;
		} 
		catch (IllegalBlockSizeException | BadPaddingException e) 
		{
			//System.out.println("ERROR: arquivo criptografado corrompido!");
			return null;
		}

		/* ---------------------------------------------------------------------- */
		
		boolean fileSignCheck = false;
		try 
		{
			fileSignCheck = Security.checkSignature(pub, filePlain, fileAsdRaw);
		} 
		catch (InvalidKeyException e) 
		{
			//System.out.println("ERROR: chave publica da verificacao da assinatura invalida!");
			return null;
		} 
		catch (SignatureException e) 
		{
			//System.out.println("ERROR: nao foi possivel verificar assinatura!");
			return null;
		}
	    
		if(!fileSignCheck)
			return null;
		
		return filePlain;
		
	}
}
