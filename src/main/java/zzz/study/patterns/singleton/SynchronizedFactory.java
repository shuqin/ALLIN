package zzz.study.patterns.singleton;

public class SynchronizedFactory {

	private static SynchronizedFactory factory;
	private static Object classLock = SynchronizedFactory.class;
	
	private SynchronizedFactory() {
		
	}
	
	public static SynchronizedFactory getFactory() {
	  synchronized(classLock) {	
		if (factory == null) {
			factory = new SynchronizedFactory();
		}
		return factory;
	   }	
	}
}
