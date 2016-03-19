package zzz.study.foundations.typeinfo;

import static java.lang.System.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionDemo {
	
	public ReflectionDemo() {
		
	}
	
	public void testFunny()
	{
		out.println("funny");
	}
	
	public void testJoy()
	{
		out.println("joy");
	}
	
	
	/**
	 * 利用反射机制运行该类的前缀为test的所有公共方法
	 *
	 */
	public void runAllTestMethods()  
	{
		try {
			Method[] allMethods = getClass().getMethods();
			for (Method method: allMethods) {
				if (method != null && method.getName().matches("test\\w+")) {
					out.println(method.getName());
					method.invoke(this, (Object[])null);
				}
			}	
		}
		catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	
	public static void main(String[] args) 
	{
       	new ReflectionDemo().runAllTestMethods();
	}

}
