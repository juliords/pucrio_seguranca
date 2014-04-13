import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;


public class Security 
{	
	private static byte[] digest(byte[] bin, String algorithm) throws Exception
	{
		MessageDigest messageDigest;
		
		messageDigest = MessageDigest.getInstance(algorithm);
	    messageDigest.update(bin);
	    
	    return messageDigest.digest();
	}
	
	public static byte[] md5(byte[] bin) throws Exception
	{
		return digest(bin, "MD5");
	}
	
	public static byte[] sha1(byte[] bin) throws Exception
	{
		return digest(bin, "SHA1");
	}
	
	private static byte[] symmetric_process(int opmode, byte[] bin, String passwd) throws Exception
	{
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(passwd.getBytes("UTF8"));
		
	    KeyGenerator keyGen = KeyGenerator.getInstance("DES");
		keyGen.init(56, random);
	    Key key = keyGen.generateKey();
	    
	    Cipher cipher;
		cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
	    cipher.init(opmode, key);
	    return cipher.doFinal(bin);
	}
	
	public static byte[] symmetric_encryption(byte[] bin, String passwd) throws Exception
	{
		return symmetric_process(Cipher.ENCRYPT_MODE, bin, passwd);
	}

	public static byte[] symmetric_decryption(byte[] bin, String passwd) throws Exception
	{
		return symmetric_process(Cipher.DECRYPT_MODE, bin, passwd);
	}
	
	public static PrivateKey private_key_from_file(String filename, String passwd) throws Exception
	{
		byte[] raw = Common.read_file(filename);
		
		byte[] plain = Security.symmetric_decryption(raw, passwd);
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(plain);
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey private_key = keyFactory.generatePrivate(keySpec);
		
		return private_key;
	}
}
