package zzz.study.foundations.annotations.outputcheck;

import java.lang.reflect.Field;
import java.util.Date;

import static zzz.study.utils.DateUtil.*;

public class OutputCheckTest {
	
	// ʹ��ע��ͷ�����ƣ����ض���ı�Ҫ������ֵ����ԣ���ע���˵��ֶΣ�
	public static String toString(Object obj) throws IllegalArgumentException, IllegalAccessException
	{
		StringBuilder sb = new StringBuilder(obj.getClass().getSimpleName() + ": \n");
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field f: fields) {
			if (f.isAnnotationPresent(OutputCheck.class)) {
				sb.append(f.getName());
				sb.append(": ");
				if (f.getType() == Date.class) {
				    sb.append(toFormattedDate((Date)f.get(obj)));
				}
				else 
				{
					sb.append(f.get(obj));
				}
				sb.append('\t');
			}
		}		
		return sb.toString();
	}
	
	public static void main(String[] args) 
	{
		Person person = new Person("12432434224", "���", getDate(1984, 8, 25), "ѧ��");
		System.out.println(person);
		try {
			System.out.println(toString(person));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
