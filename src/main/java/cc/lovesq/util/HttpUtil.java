package cc.lovesq.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;


/**
 * @Described：Http请求工具类
 * @author YHJ create at 2014年5月14日 下午4:38:56
 * @ClassNmae com.aliyun.houyi.compass.v2.util.HttpUtil
 */
public class HttpUtil {
	
	public final static String encode = "UTF-8";

	/**
	 * 通过http的post方式请求数据  connectionTimeOut:建立连接的超时时间，soTimeOut：等待返回结果的超时时间
	 * @param urlString
	 * @param params
	 * @param encode
	 * @param connectionTimeOut
	 * @param soTimeOut
	 * @return
	 * @throws IOException 
	 * @throws HttpException 
	 * @throws Exception
	 * @Author YHJ create at 2014年5月14日 下午4:36:02
	 */
	public static String doPost(String urlString, Map<String, String> params) throws HttpException, IOException {
		PostMethod method = new PostMethod(urlString);
		HttpClient client = null;
		try {
			Set<String> keys = params.keySet();
			NameValuePair[] values = new NameValuePair[keys.size()];
			int i = 0;
			for (String key : keys) {
				NameValuePair v = new NameValuePair();
				v.setName(key);
				v.setValue(params.get(key));
				values[i] = v;
				i++;
			}
			
			client = new HttpClient();
			client.getHostConfiguration().setHost(urlString, 80, "http");
			client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);// 建立连接的超时时间
			client.getHttpConnectionManager().getParams().setSoTimeout(30000);// 等待请求结果的超时时间
			if (StringUtils.isNotBlank(encode))
				client.getParams().setParameter(
						HttpMethodParams.HTTP_CONTENT_CHARSET, encode);
			method.setRequestBody(values); // 使用 POST 方式提交数据
			int state = client.executeMethod(method);   //返回的状态 
			if(state != HttpStatus.SC_OK){
				throw new RuntimeException("HttpStatus is "+state);
			} 
			return inputStreamToString(method.getResponseBodyAsStream(), encode);
		} finally {
			//releaseConnection方法不能关闭socket连接
            //使用SimpleHttpConnectionManager的shutdown方法强制关闭
			method.releaseConnection();
			if (client != null ) {
	            HttpConnectionManager manager = client.getHttpConnectionManager();
	            if (manager instanceof SimpleHttpConnectionManager) {
	                SimpleHttpConnectionManager tmp = (SimpleHttpConnectionManager)manager;
	                tmp.shutdown();
	            }
			}
		}
	}

	/**
	 * 通过http的get方式请求数据 connectionTimeOut:建立连接的超时时间，soTimeOut：等待返回结果的超时时间
	 * @param urlString
	 * @param encode
	 * @param connectionTimeOut
	 * @param soTimeOut
	 * @return
	 * @throws Exception
	 * @Author YHJ create at 2014年5月14日 下午4:37:25
	 */
	public static String doGet(String urlString) throws Exception{
		GetMethod method = new GetMethod(urlString);
		try {
			HttpClient client = new HttpClient();
			client.getHostConfiguration().setHost(urlString, 80, "http");
			client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);// 建立连接的超时时间
			client.getHttpConnectionManager().getParams().setSoTimeout(30000);// 等待请求结果的超时时间
			int state = client.executeMethod(method);   //返回的状态 
			if(state != HttpStatus.SC_OK){
				throw new Exception("HttpStatus is "+state);
			} 
			String response = inputStreamToString(method.getResponseBodyAsStream(), encode);
			return response; // response就是最后得到的结果
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			method.releaseConnection();
		}
	}
	
	/**
	 * inputStream转换String
	 * @param is
	 * @param encode
	 * @return
	 * @Author YHJ create at 2014年5月14日 下午2:41:44
	 */
	private static String inputStreamToString(InputStream is, String encode) {
		StringBuffer out = new StringBuffer();
		try {
			byte[] b = new byte[4096];
			for (int n; (n = is.read(b)) != -1;) {
				out.append(new String(b, 0, n, StringUtils.isBlank(encode)?"UTF-8":encode));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return out.toString();
	}

}
