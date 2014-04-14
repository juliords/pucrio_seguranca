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
}
