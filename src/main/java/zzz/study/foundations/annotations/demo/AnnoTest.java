package zzz.study.foundations.annotations.demo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class AnnoTest {

    public static void main(String[] args) {
        Method[] methods = Testable.class.getDeclaredMethods();

        for (Method m : methods) {
            if (m.isAnnotationPresent(CustomizedTest.class)) {
                CustomizedTest anno = m.getAnnotation(CustomizedTest.class);
                System.out.println("Test(method: " + m.getName()
                        + "  id = " + anno.id()
                        + "  desc = " + anno.desc()
                        + ")");
                try {
                    m.invoke(new Testable(), (Object[]) null);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
