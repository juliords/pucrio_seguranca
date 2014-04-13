import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.KeyGenerator;


public class Main 
{
	public static void main(String[] args) throws Exception
	{
		String privKeyFile = "../sample/userpriv";
		String pubKeyFile = "../sample/userpub";
		PrivateKey privateKey = Security.private_key_from_file(privKeyFile, "segredo".getBytes("UTF8"));
		PublicKey publicKey = Security.public_key_from_file(pubKeyFile);
		
		System.out.println("Key check: "+(Security.check_key_pair(privateKey, publicKey) ? "True" : "False"));
		
		String indexFile = "../sample/index";
		byte[] index_key = Security.rsa_decrypt_file(privateKey, indexFile+".env");
		
		byte[] index_plain = Security.symmetric_decryption(Common.read_file(indexFile+".enc"), index_key);
		System.out.println("=========== BEGIN OF FILE ================");
		System.out.println(new String(index_plain, "UTF8"));
		System.out.println("=============END OF FILE =================");
	    System.out.println("Digest check: "+(Security.signature_check(publicKey, index_plain,Common.read_file(indexFile+".asd")) ? "True" : "False"));
		
	}
}
