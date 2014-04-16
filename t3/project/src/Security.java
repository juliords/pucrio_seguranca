import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;


public class Security 
{	
	private static byte[] digest(byte[] bin, String algorithm) throws NoSuchAlgorithmException
	{

		MessageDigest messageDigest;

		messageDigest = MessageDigest.getInstance(algorithm);
		messageDigest.update(bin);

		return messageDigest.digest();
	}

	public static byte[] md5(byte[] bin) throws NoSuchAlgorithmException
	{
		return digest(bin, "MD5");
	}

	public static byte[] sha1(byte[] bin) throws NoSuchAlgorithmException
	{
		return digest(bin, "SHA1");
	}
	
	private static byte[] symmetricProcess(int opmode, byte[] raw, byte[] passwd) 
				throws 	NoSuchAlgorithmException, 
						NoSuchPaddingException, 
						InvalidKeyException, 
						IllegalBlockSizeException, 
						BadPaddingException
	{
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(passwd);
		
	    KeyGenerator keyGen = KeyGenerator.getInstance("DES");
		keyGen.init(56, random);
	    Key key = keyGen.generateKey();
	    
	    Cipher cipher;
		cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
	    cipher.init(opmode, key);
	    return cipher.doFinal(raw);
	}
	
	public static byte[] symmetricEncryption(byte[] raw, byte[] passwd)
			throws 	NoSuchAlgorithmException, 
					NoSuchPaddingException, 
					InvalidKeyException, 
					IllegalBlockSizeException, 
					BadPaddingException
	{
		return symmetricProcess(Cipher.ENCRYPT_MODE, raw, passwd);
	}

	public static byte[] symmetricDecryption(byte[] raw, byte[] passwd)
			throws 	NoSuchAlgorithmException, 
					NoSuchPaddingException, 
					InvalidKeyException, 
					IllegalBlockSizeException, 
					BadPaddingException
	{
		return symmetricProcess(Cipher.DECRYPT_MODE, raw, passwd);
	}
	
	public static PrivateKey loadPrivateKey(byte[] raw, byte[] passwd) 
			throws 	InvalidKeyException, 
					NoSuchAlgorithmException, 
					NoSuchPaddingException, 
					IllegalBlockSizeException, 
					BadPaddingException, 
					InvalidKeySpecException
	{
		byte[] plain = Security.symmetricDecryption(raw, passwd);
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(plain);
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(keySpec);
	}
	
	public static PublicKey loadPublicKey(byte[] raw) 
			throws 	NoSuchAlgorithmException, 
					InvalidKeySpecException
	{
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(raw);
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(keySpec);
	}

	public static boolean checkSignature(PublicKey pub, byte[] plain, byte[] sign) 
			throws 	NoSuchAlgorithmException, 
					InvalidKeyException, 
					SignatureException
	{
		Signature sig = Signature.getInstance("MD5WithRSA");
		
		sig.initVerify(pub);
	    sig.update(plain);
	    return sig.verify(sign);
	}
	
	public static boolean checkKeyPair(PrivateKey priv, PublicKey pub) 
			throws 	NoSuchAlgorithmException, 
					InvalidKeyException, 
					SignatureException
	{
		byte[] raw = new byte[512];
		(new Random()).nextBytes(raw);
		
		Signature sig = Signature.getInstance("MD5WithRSA");
	    sig.initSign(priv);
	    sig.update(raw);

	    return checkSignature(pub, raw, sig.sign());
	}
	
	public static byte[] asymmetricDecryption(byte[] raw, Key key) 
			throws 	NoSuchAlgorithmException, 
					NoSuchPaddingException, 
					InvalidKeyException, 
					IllegalBlockSizeException, 
					BadPaddingException
	{
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	    cipher.init(Cipher.DECRYPT_MODE, key);
		
		return cipher.doFinal(raw);
	}
}
