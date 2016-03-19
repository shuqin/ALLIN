package zzz.study.foundations.typeinfo;

public class CastOnlyInRuntime {
	
	public static void main(String[] args) 
	{
		Integer integer = 0;
		String string = "I am a string.";
		//! 无法通过编译 integer = (Integer) string;
		Object object = string;
		// integer = (Integer) object; // 类型转换只能在基类与派生类之间进行，注意，这里可以通过编译；
		System.out.println(object);
		
		object = integer;
		System.out.println(object);
		
		Base base = new Base();
		// Derived derived = (Derived) base; // 能够通过编译，但会发生运行时错误
		
		//! 无法通过编译 base = (Base)new Unknown();
	}

}

class Base  {
	
}

class Derived extends Base {
	
}

class Unknown {
	
}

/**
 * NOTE:
 * Java 的类型转换主要发生在 运行时；基类与派生类之间进行类型转换，可以通过编译时；
 * 如果进行类型转换的两个类并不是基类和派生类的关系，那么，是无法通过编译的。
 */

