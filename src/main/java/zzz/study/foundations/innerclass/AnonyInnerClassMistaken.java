package zzz.study.foundations.innerclass;

public class AnonyInnerClassMistaken {

//!Error	public AnonyInner getAnonyInnerClass() {
//!Error	   return new AnonyInner() {
//		    	  int i = 0;
//			       public int getValue() {
//				      return i;
//			       }
//		       };
//	        }

}

/*
 * Note:
 *  创建匿名内部类时，	 return new AnonyInner() { // fields and methods }
 *	并不是指 创建 AnonyInner 类的定义，并创建和返回一个 AnonyInner 对象的引用,
 *	而是指   基类或抽象类，或接口 AnnoyInner 已经存在，此时创建一个继承或实现 AnonyInner 的无名类的定义，
 *	并创建和返回一个相应对象的引用
 *
 */
 

