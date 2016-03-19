package zzz.study.foundations.inface.inner;

import java.lang.reflect.Field;

import zzz.study.foundations.inface.inner.InterfaceWithConstant;

public class ReadingConstantsInInterface {
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException
	{
		System.out.println("--------------- 直接访问接口字段 -----------------");
		for (int i = 0; i < 5; i++) {
			System.out.println(InterfaceWithConstant.PI);    
		    System.out.println(InterfaceWithConstant.VARIABLE);
		}
		
		System.out.println("--------------- 通过反射访问接口字段 -----------------");
		getConstantsProperty(InterfaceWithConstant.class);
	}
	
	public static void getConstantsProperty(Class<?> type) throws IllegalArgumentException, IllegalAccessException
	{
		System.out.println("Is a interface ? " + (type.isInterface() ? "Yes" : "No"));
		Field[] fields = type.getFields();
		for (Field field: fields) {
			System.out.println("字段名: " + field.getName());
			System.out.println("字段值: " + field.get(field));
			System.out.println("字段类型: " + field.getType());
			System.out.println("字段属性: " + field.getModifiers());
			System.out.println("字段描述: " + field);
		}
	}

}

/*
 * 接口中的常量默认是 public static final 的.
 */