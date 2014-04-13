import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.KeyGenerator;


public class Main 
{
	private static void test_crypt() throws Exception
	{
		String test = "qwe123";
		System.out.println("Original: "+test);
		
		byte[] cipher = Security.symmetric_encryption(test.getBytes("UTF8"), "segredo");
		System.out.println("Cifra: "+Common.binToHex(cipher));
		
		byte[] original = Security.symmetric_decryption(cipher, "segredo");
		System.out.println("Recuperado: "+(new String(original, "UTF8")));
	}
	
	private static void test_prng() throws Exception
	{
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed("password".getBytes("UTF8"));
		
		for(int i = 0; i < 5; i++)
			System.out.println(random.nextInt() % 100);
	}
	
	private static void test_key() throws Exception
	{
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed("password".getBytes("UTF8"));
		
	    KeyGenerator keyGen = KeyGenerator.getInstance("DES");
		keyGen.init(56, random);
	    Key key = keyGen.generateKey();
	    System.out.println("Chave usada: "+key.getEncoded().toString());
	}
	
	private static void test_private_key(String filename) throws Exception
	{
		byte[] private_key_plain = Security.symmetric_decryption(Common.read_file(filename), "segredo");
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(private_key_plain);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey private_key = keyFactory.generatePrivate(keySpec); 
		System.out.println(Common.binToHex(private_key.getEncoded()));
	}
	
	public static void main(String[] args) throws Exception
	{
		test_crypt();
	}
}
