package cc.lovesq.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class MD5 {
	
	private static Logger logger = Logger.getLogger(MD5.class);
	
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	
    public static String getMD5(byte[] bytes) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        char str[] = new char[16 * 2];
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(bytes);
            byte tmp[] = md.digest();
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new String(str);
    }
 
    public static String getMD5(String value) {
        String result = "";
        try {
            result = getMD5(value.getBytes("UTF-8"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }
    
    public static String getFormatedDate(Date date) {
    	return SIMPLE_DATE_FORMAT.format(date);
    }

}
