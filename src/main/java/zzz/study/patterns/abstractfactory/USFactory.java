package zzz.study.patterns.abstractfactory;

/**
 * 
 *  具体工厂： 美国工厂， 生产针对美国市场的具体产品(USCreditCheck,USBillingCheck)
 *
 */
public class USFactory implements AbstractFactory {

	private static USFactory us ;
	private static Object classLock = USFactory.class;
	
	private USFactory() {
		
	}
	
	public static USFactory getUSFactory() {
		synchronized (classLock) {
			if (us == null) {
				us = new USFactory();
			}
		}
		return us;
	}
	
	public IPad createIPad() {
		return new USIPad();
	}

	public IMac createIMac() {
		return new USIMac();
	}
}
