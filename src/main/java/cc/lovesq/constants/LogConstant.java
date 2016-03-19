package cc.lovesq.constants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogConstant {
	
	private final static Log LOG = LogFactory.getLog("LogConstant");
	
	public static void error(String msg,Throwable e){
		LOG.error(msg, e);
	}
	
	public static void error(String msg){
		LOG.error(msg);
	}
	
	public static void warn(String msg,Throwable e){
		LOG.warn(msg, e);
	}
	public static void warn(String msg){
		LOG.warn(msg);
	}
	
	public static void info(String msg,Throwable e){
		LOG.info(msg, e);
	}
	public static void info(String msg){
		LOG.info(msg);
	}

}

