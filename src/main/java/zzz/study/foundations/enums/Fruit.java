package zzz.study.foundations.enums;

/***
 * 
 *  演示枚举的基本用法：常用函数和惯用法
 *
 */

public enum Fruit  {
	
	APPLE("An apple a day keeps the doctor away."), 
	BANANA("good fruit."), 
	PEAR("good for thirsty."), 
	GRAPE("Those who can't obtain the grape would say..."), 
	WATERMELON("very good fruit in the hot summer."), 
	TOMATO("my favorite fruit");
	
	private String desc;
	
	Fruit(String desc) {
		this.desc = desc;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	public String toString() {
		
		String s = this.name();
		return s.charAt(0) + s.substring(1).toLowerCase();
	}
	
	public static String getContent() {
		StringBuilder sb = new StringBuilder("Fruits: ");
		for (Fruit fruit : Fruit.values()) {
			sb.append(fruit.name() + " ");
		}
		return sb.toString();
	}
	
	public String choice() {

		switch (this) {
		  case APPLE:
			  return "APPLE";
		  case BANANA:  
			  return "BANANA";
		  case PEAR:  
			  return "PEAR";	  
		  case GRAPE:
			  return "GRAPE";
		  case WATERMELON:
			  return "WATERMELON";
		  case TOMATO:
			  return "TOMATO";
		  default:
			  return "UNKNOWN";	  
		} 
	}
	
	
	
	public static void main(String[] args) {
		
		System.out.println("--------------------------");
		for (Fruit fruit: Fruit.values()) {
			System.out.println("You have chosen " + fruit.choice() + "\n" + fruit.getDesc());
			System.out.println("Is an apple? " + fruit.compareTo(APPLE));
			System.out.println("Is an apple? " + fruit.equals(APPLE));
			System.out.println("Is an apple? " + (fruit==APPLE));
			System.out.println("--------------------------");
		}
		
	
		System.out.println("--------------------------");
		System.out.println(Fruit.getContent());
		
		System.out.println("--------------------------");
		
		try {
			Fruit fruit = Enum.valueOf(Fruit.class, "GRAPE");
			System.out.println(fruit);
			
			Fruit fruit2 = Enum.valueOf(Fruit.class, "ABBB");
			System.out.println(fruit);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}
	

}
