import java.security.PrivateKey;
import java.security.PublicKey;


public class Main 
{
	public static void main(String[] args) throws Exception
	{
		String privKeyPath = "../sample/userpriv";
		byte[] privKeyRaw = Common.readFile(privKeyPath);
		String pubKeyPath = "../sample/userpub";
		byte[] pubKeyRaw = Common.readFile(pubKeyPath);
		
		PrivateKey privateKey = Security.loadPrivateKey(privKeyRaw, "segredo".getBytes("UTF8"));
		PublicKey publicKey = Security.loadPublicKey(pubKeyRaw);
		
		boolean keyPairCheck = Security.checkKeyPair(privateKey, publicKey);
		System.out.println("Key check: "+(keyPairCheck ? "True" : "False"));
		
		String indexPath = "../sample/index";
		byte[] indexRaw = Common.readFile(indexPath+".env");
		byte[] indexKeyPlain = Security.asymmetricDecryption(indexRaw, privateKey);
		
		byte[] indexEnvRaw = Common.readFile(indexPath+".enc");
		byte[] indexPlain = Security.symmetricDecryption(indexEnvRaw, indexKeyPlain);
		System.out.println("=========== BEGIN OF FILE ================");
		System.out.println(new String(indexPlain, "UTF8"));
		System.out.println("=============END OF FILE =================");
		
		byte[] indexAsdRaw = Common.readFile(indexPath+".asd");
		boolean indexSignCheck = Security.checkSignature(publicKey, indexPlain, indexAsdRaw);
	    System.out.println("Digest check: "+(indexSignCheck ? "True" : "False"));
		
	}
}
