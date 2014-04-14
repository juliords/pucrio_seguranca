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
        
        return keyBytes;
	}
	
	public static byte[] readCryptedFile(String filename, PrivateKey priv, PublicKey pub)
	{
		String filePath = filename;
		byte[] fileRaw, fileEnvRaw, fileAsdRaw;
		try 
		{
			fileRaw = FileHandler.readFile(filePath+".enc");
			fileEnvRaw = FileHandler.readFile(filePath+".env");
			fileAsdRaw = FileHandler.readFile(filePath+".asd");
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
			//System.out.println("ERROR: chave privada do envelope digital inválida!");
			return null;
		} 
		catch (IllegalBlockSizeException | BadPaddingException e) 
		{
			//System.out.println("ERROR: envelope digital inválido!");
			return null;
		}
		catch (NoSuchAlgorithmException | NoSuchPaddingException e) 
		{
			e.printStackTrace();
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
			//System.out.println("ERROR: chave do arquivo criptografado inválida!");
			return null;
		} 
		catch (IllegalBlockSizeException | BadPaddingException e) 
		{
			//System.out.println("ERROR: arquivo criptografado corrompido!");
			return null;
		}
		catch (NoSuchAlgorithmException | NoSuchPaddingException e) 
		{
			e.printStackTrace();
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
			//System.out.println("ERROR: chave pública da verificação da assinatura inválida!");
			return null;
		} 
		catch (SignatureException e) 
		{
			//System.out.println("ERROR: não foi possível verificar assinatura!");
			return null;
		}
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
			return null;
		} 
	    
		if(!fileSignCheck)
			return null;
		
		return filePlain;
		
	}
}
