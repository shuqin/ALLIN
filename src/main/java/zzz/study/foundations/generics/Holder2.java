package zzz.study.foundations.generics;

import zzz.study.foundations.inheritance.Base;
import zzz.study.foundations.inheritance.Derived;
import zzz.study.foundations.sharingclasses.Dog;

/**
 * Holder2:  单对象的泛型实现
 * 在运行时指定可持有对象的类型，且只能持有第一次指定的类型的对象
 */
public class Holder2<T> {
	
	private T obj;
	
	public Holder2(T obj) {
		this.obj = obj;
	}
	
	public void set(T obj) {
		this.obj = obj;
	}
	
	public T get() {
		return this.obj;
	}
	
	public static void main(String[] args) {
		
		System.out.println("********** 泛型： 只能容纳第一次指定类型的对象 ************");
		
		// 可以容纳任何 Dog 对象的 Holder2 对象
		Holder2<Dog> dogholder = new Holder2<Dog>(new Dog("this is a dog holder"));  
		System.out.println(dogholder.get());
		dogholder.set(new Dog("another dog"));
		System.out.println(dogholder.get());
		
//!		         dogholder 只能持有第一次指定的类型的对象【Dog】
//!Error		 dogholder.set("Changed to a string.");
//!Error		 dogholder.set(3.14);
		
		// 要想容纳 String 对象，则要创建新的 Holder2 对象
		Holder2<String> strholder = new Holder2<String>("this is a string holder");
		System.out.println(strholder.get());
		strholder.set("another string");
        System.out.println(strholder.get());
		
		System.out.println("************ 不安全的容器 ***************");
		
		// 可以容纳各种对象的不安全容器，取出容器中对象的人不能预测里面会是什么，可能抛出运行时类型错误异常
		Holder2 unsafeholder = new Holder2(new Dog("GouGou"));
		System.out.println(unsafeholder.get());
		unsafeholder.set("now is a string."); 
		System.out.println(unsafeholder.get()); 
		
		System.out.println("************* 泛型与类型转换 ****************");
		
		Holder2<Base> hbase = new Holder2<Base>(new Base());
		hbase.get().say();
		hbase.set(new Derived("derived"));
		hbase.get().say();
		
		hbase.set(new Derived("derived"));
		((Base)(hbase.get())).say();
		
		Holder2<Derived> hderived = new Holder2<Derived>(new Derived("Derived"));
		hderived.get().say();
		
	}

}
