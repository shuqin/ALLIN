package zzz.study.patterns.singleton;

import java.lang.reflect.Constructor;

public class ReflectionRuinSingleton {

    public static void main(String[] args) {
        try {
            Singleton s = Singleton.INSTANCE;

            Constructor sc = Singleton.class.getDeclaredConstructor();
            sc.setAccessible(true);
            Singleton sr1 = (Singleton) sc.newInstance();
            System.out.println(sr1);
            Singleton sr2 = (Singleton) sc.newInstance();

            System.out.println(s == sr1);
            System.out.println(sr1 == sr2);
        } catch (Exception e) {
            System.err.println(e.getCause().getMessage());
        }
    }
}
