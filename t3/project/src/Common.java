import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Common 
{
	public static String binToHex(byte[] bin)
	{
		StringBuffer buf = new StringBuffer();
	    for(int i = 0; i < bin.length; i++) {
	       String hex = Integer.toHexString(0x0100 + (bin[i] & 0x00FF)).substring(1);
	       buf.append((hex.length() < 2 ? "0" : "") + hex);
	    }
		return buf.toString();
	}

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
}
