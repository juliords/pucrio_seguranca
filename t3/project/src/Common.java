import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


public class Common 
{
	public static String binToHex(byte[] bin)
	{
		StringBuffer buf = new StringBuffer();
	    for(int i = 0; i < bin.length; i++) 
	    {
	       String hex = Integer.toHexString(0x0100 + (bin[i] & 0x00FF)).substring(1);
	       buf.append((hex.length() < 2 ? "0" : "") + hex);
	    }
		return buf.toString();
	}
	
	/* http://stackoverflow.com/questions/140131/convert-a-string-representation-of-a-hex-dump-to-a-byte-array-using-java */
	public static byte[] hexToBin(String s) 
	{
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) 
	    {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	public static Integer randi(Integer min, Integer max)
	{
		return Math.abs((new Random()).nextInt()) % (max-min+1) + min;
	}
	
	public static String genSalt()
	{
		return String.format("%09d", randi(0, 999999999));
	}
	
	public static String genPasswdHash(String plain, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		return Common.binToHex(Security.md5((plain+salt).getBytes("UTF8")));
	}
}
