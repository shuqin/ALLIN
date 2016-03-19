package zzz.study.foundations.basic;

public class StaticFactoryMethod {
	
	public static void main(String[] args) {
		
		// 普通的构造器不能为重复的值返回同一个实例
		Integer itg25 = new Integer(25);
		Integer another25 = new Integer(25);
		System.out.println("itg25.hashCode(): " + itg25.hashCode());
		System.out.println("another25.hashCode(): " + another25.hashCode());
		System.out.println("itg25 == another25 ? " + (itg25 == another25 ? "yes" : "no"));
		System.out.println("itg25.equals(another25) ? " + (itg25.equals(another25) ? "yes" : "no"));
		
		System.out.println("---------------------------------------");
		
		// 静态工厂方法总是为重复的调用返回同一个实例。
		// 这是因为不可变类将构建好的实例缓存起来，进行重复使用，而不用重复创建相同的对象。
		// 适用场合：程序经常请求相同的对象，并且创建对象的代价较高。
		Integer int25 = Integer.valueOf(25);
		Integer same25 = Integer.valueOf(25);
		System.out.println("int25.hashCode(): " + int25.hashCode());
		System.out.println("same25.hashCode(): " + same25.hashCode());
		System.out.println("int25 == same25 ? " + (int25 == same25 ? "yes" : "no"));
		System.out.println("int25.equals(same25) ? " + (int25.equals(same25) ? "yes" : "no"));
	}
}
