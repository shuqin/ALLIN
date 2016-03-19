package zzz.study.foundations.basic;

import static java.lang.System.out;

public class VariableParemeters {
	
	/**
	 * 计算可变数目的整数的和
	 * @param list
	 * @return 整数的和
	 */
	public static int sum(int... list) 
	{
		int  sum = 0;
		for (int i=0; i < list.length; i++) {
			sum += list[i];
		}
		return sum;
	}
	
	public static String required(String tip, String...args) {
		 StringBuilder required = new StringBuilder(tip);
		 for (String arg: args) {
			 required.append(arg);
			 required.append(" ");
		 }
		 required.append("\ntotal: " + args.length);
		 return required.toString();
	}
	
    public static void main(String[] args) {
		
		out.println("sum = " + sum());
		out.println("sum = " + sum(1,2,3));
		out.println("sum = " + sum(1,2,3,4));
		out.println("sum = " + sum(1,2,3,4,5));
		out.println("sum = " + sum(new int[]{3,5,7,9}));
		
		out.println(required("Required: ", "one"));
		out.println(required("Required: ", "one", "two"));
	}

}


/*
 * Note:
 * 1. 可变参数列表将被转化为数组处理，其作用效果相当于可变数组
 * 2. 可变参数容易导致方法重载的二义性以及代码的混乱，须慎用。
 * 3. 静态导入： import static 包名.类名.[*,静态成员名,静态方法名] 
 *    在引用静态成员名或静态方法名时，不必再添加类名的限定。
 */

 