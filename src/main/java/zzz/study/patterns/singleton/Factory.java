package zzz.study.patterns.singleton;

public class Factory {

	private static Factory factory;
	
	private Factory() {
		
	}
	
	public static Factory getFactory() {
		if (factory == null) {
			factory = new Factory();
		}
		return factory;
	}
}
