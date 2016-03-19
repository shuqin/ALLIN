package cc.lovesq.util;

import java.io.UnsupportedEncodingException;
import java.net.URL;

public class UrlUtils {
	
	public static String getReturnData(String urlString) throws UnsupportedEncodingException {
		String res = ""; 
		try { 
			URL url = new URL(urlString);
			java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				res += line;
			}
			in.close();
		} catch (Exception e) {
			System.err.println("error in wapaction,and e is " + e.getMessage());
		}
		return res;
	}

}
