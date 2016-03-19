package zzz.study.foundations.innerclass;

import zzz.study.foundations.innerclass.authority.AccessAuthority;

public class AccessAuthortyTestOutOfPackage {
	
	public static void main(String[] args)
	{
		OuterAssistance.main(args);
		
		System.out.println(" ************ Enter into the main: " + AccessAuthortyTestOutOfPackage.class.getName() + "*************");
		
		// 无法直接创建所属包外部的其它类的对象及包外类【默认、公共内部类】的对象
		// System.out.println(new Relative(5));
		
		AccessAuthority access = new AccessAuthority();
		
		System.out.println(access.new PublicInnerclass(1));
		//System.out.println(access.new Innerclass(2));
		//!Error 在辅助类中无法创建其主类的私有内部类的对象。
		//System.out.println(access.new PrivateInnerclass(3));
		
		// 可以间接创建包内其它类的【私有、默认、公有内部类】的对象。
        System.out.println(" -------- 间接创建 ------------- ");
		System.out.println(access.getRelative());
		System.out.println(access.getInner());
		System.out.println(access.getPrivateInner());
		System.out.println(access.getPublicInner());
		
		System.out.println();
		
		
	}

}

class OuterAssistance {
	
	public static void main(String[] args)
	{
		System.out.println(" ************ Enter into the main: " + OuterAssistance.class.getName() + "*************");
		
		// 无法直接创建所属包外部的其它类的对象及包外类【默认、公共内部类】的对象
		//System.out.println(new Relative(5));
		
		AccessAuthority access = new AccessAuthority();
		
		System.out.println(access.new PublicInnerclass(1));
		//System.out.println(access.new Innerclass(2));
		//!Error 在辅助类中无法创建其主类的私有内部类的对象。
		//System.out.println(access.new PrivateInnerclass(3));
		
		// 可以间接创建所有类的【私有、默认、公有内部类】的对象。
        System.out.println(" -------- 间接创建 ------------- ");
		System.out.println(access.getRelative());
		System.out.println(access.getInner());
		System.out.println(access.getPrivateInner());
		System.out.println(access.getPublicInner());
		
		System.out.println();
		
		
	}
}
