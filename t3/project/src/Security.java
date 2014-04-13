import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;


public class Security 
{	
	private static byte[] symmetric_process(int opmode, byte[] bin, byte[] passwd) throws Exception
	{
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(passwd);
		
	    KeyGenerator keyGen = KeyGenerator.getInstance("DES");
		keyGen.init(56, random);
	    Key key = keyGen.generateKey();
	    
	    Cipher cipher;
		cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
	    cipher.init(opmode, key);
	    return cipher.doFinal(bin);
	}
	
	public static byte[] symmetric_encryption(byte[] bin, byte[] passwd) throws Exception
	{
		return symmetric_process(Cipher.ENCRYPT_MODE, bin, passwd);
	}

	public static byte[] symmetric_decryption(byte[] bin, byte[] passwd) throws Exception
	{
		return symmetric_process(Cipher.DECRYPT_MODE, bin, passwd);
	}
	
	public static PrivateKey private_key_from_file(String filename, byte[] passwd) throws Exception
	{
		byte[] raw = Common.read_file(filename);
		
		byte[] plain = Security.symmetric_decryption(raw, passwd);
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(plain);
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(keySpec);
	}
	
	public static PublicKey public_key_from_file(String filename) throws Exception
	{
		byte[] raw = Common.read_file(filename);
		
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(raw);
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(keySpec);
	}

	public static boolean digest_check(PublicKey pub, byte[] plain, byte[] sign) throws Exception
	{
		Signature sig = Signature.getInstance("MD5WithRSA");
		
		sig.initVerify(pub);
	    sig.update(plain);
	    
	    return sig.verify(sign);
	}
	
	public static boolean check_key_pair(PrivateKey priv, PublicKey pub) throws Exception
	{
		byte[] raw = new byte[512];
		(new Random()).nextBytes(raw);
		
		Signature sig = Signature.getInstance("MD5WithRSA");
	    sig.initSign(priv);
	    sig.update(raw);

	    return digest_check(pub, raw, sig.sign());
	}
	
	public static byte[] rsa_decrypt_file(Key key, String filename) throws Exception
	{
		byte[] raw = Common.read_file(filename);
		
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	    cipher.init(Cipher.DECRYPT_MODE, key);
		
		return cipher.doFinal(raw);
	}
}
