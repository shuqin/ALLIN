package zzz.study.patterns.flyweight;

import java.util.HashMap;
import java.util.Map;

/*
 *  ChemicalFactory 为享元工厂，创建、存储和管理享元对象 Chemical 对象。
 * 
 */

public class ChemicalFactory {
	
	private static Map<String, Chemical> chemicals = new HashMap<String, Chemical>();
	
	private static ChemicalFactory factory;
	
    private static Object classLock = ChemicalFactory.class;
	
	ChemicalFactory() {
		
	}
	
	public static ChemicalFactory getFactory() {
	  synchronized(classLock) {	
		if (factory == null) {
			factory = new ChemicalFactory();
		}
		return factory;
	   }	
	}
	
	/* 
	 * 内部类 Chemical 定义化学品的基本属性（化学物质对象的不可变部分）
	 */
	
	private class Chemical implements ChemicalInterface {
		
		private String name;
		private String symbol;
		private double atomicWeight;
		
		public Chemical(String name, String symbol, double atomicWeight) {
			this.name = name;
			this.symbol = symbol;
			this.atomicWeight = atomicWeight;
		}
		
		public String getName() {
			return name;
		}
		
		public String getSymbol() {
			return symbol;
		}
		
		public double getAtomicWeight() {
			return atomicWeight;
		}
		
		public String toString() {
			return name + "(" + symbol + ")[" + atomicWeight + "]"; 
		}
		
	}
	
	static {
		
		ChemicalFactory factory = getFactory();
		chemicals.put("cabon", factory.new Chemical("cabon", "C", 12));
		chemicals.put("sulfur", factory.new Chemical("sulfur", "S", 32));
		chemicals.put("saltpeter", factory.new Chemical("saltpeter", "KN03", 101));

		//...
	}
	
	public static Chemical getChemical(String name) {
		return (Chemical) chemicals.get(name.toLowerCase());
	}
	

}
