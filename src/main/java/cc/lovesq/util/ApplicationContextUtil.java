package cc.lovesq.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextUtil {

	private static ClassPathXmlApplicationContext applicationContext = null;
	
	private ApplicationContextUtil() {}
	
	static {
		if (applicationContext == null) {
			applicationContext = new ClassPathXmlApplicationContext("javathreading-spring-base.xml");
		}
	}
	
	public static ClassPathXmlApplicationContext getApplicationContextInstance() {
		if (applicationContext == null) {
			return new ClassPathXmlApplicationContext("javathreading-spring-base");
		}
		return applicationContext;
	}
	
	public static Object getBean(String beanId) {
		return applicationContext.getBean(beanId);
	}
	
}
