package zzz.study.foundations.generics;

import zzz.study.foundations.sharingclasses.Dog;

/**
 * 演示泛型方法
 * NOTE: 泛型方法具有更大的灵活性
 */
public class GenericMethod {
	
	
	public <T> void paramInfo(T x) 
	{
		System.out.println("paramInfo(T) called.");
		System.out.println("type: " + x.getClass().getName());
		System.out.println("value: " + x);
	}
	
	public <T,V> void paramInfo(T x, V y)
	{
		System.out.println("paramInfo(T,V) called.");
		System.out.println( "(" + x + " , " + y + ")");
	}
	
	public <V> void paramInfo(String info, V y)
	{
		System.out.println("paramInfo(String,V) called.");
		System.out.println(info + ": " + y);
	}
	
	public static void main(String[] args) {
		
		GenericMethod gm = new GenericMethod();
		gm.paramInfo(new Dog("GouGou"));
		gm.paramInfo("my dream");
		gm.paramInfo(0L);
		gm.paramInfo(0);
		gm.paramInfo(gm);
		
		gm.paramInfo("my dream", "a beautiful home");
		gm.paramInfo(5L, 0);
	}

}
