package zzz.study.patterns.factoryMethod;

import java.text.DateFormat;
import java.util.Date;

public class CreditCheckFactory {	
	
	public static CreditCheck createCreditCheck() {
		if (isAgentUp()) 
			return new CreditCheckOnline();
		else
			return new CreditCheckOffline();
			
	}
	
	public static boolean isAgentUp() {
		String time = DateFormat.getTimeInstance().format(new Date());
		System.out.println("当前时间：" + time);
		int hour = Integer.parseInt(time.substring(0, time.indexOf(':')));
		if (hour >= 8 && hour <= 20)
		  return true;
		else
		  return false;
	}

}
