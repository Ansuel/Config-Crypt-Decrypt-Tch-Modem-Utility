package decrypt_config;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Formatter;
import java.util.Random;

import javax.xml.bind.DatatypeConverter;

public class string_util {

	public static String toHex(String text) throws UnsupportedEncodingException {
		byte[] myBytes = text.getBytes("UTF-8");

	    return DatatypeConverter.printHexBinary(myBytes);
	}
	
	public static String getRandomHexString(int length){
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append((char)(random.nextInt(128)));
        }

        return sb.toString();
    }
	
	public static String toHexString(byte[] bytes) {
		Formatter formatter = new Formatter();
		
		for (byte b : bytes) {
			formatter.format("%02x", b);
		}
		
		String result = formatter.toString();
		
		formatter.close();

		return result;
	}
	
	public static byte[] toByteArray(String s) {
	    return new BigInteger(s,16).toByteArray();
	}
}
