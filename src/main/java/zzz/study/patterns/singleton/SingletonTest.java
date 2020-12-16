package zzz.study.patterns.singleton;

public class SingletonTest {

    public static void main(String[] args) {
        
        Singleton sl21 = Singleton.INSTANCE;
        Singleton sl22 = Singleton.INSTANCE;
        System.out.println(sl21 == sl22);

        Singleton2 sl31 = Singleton2.getInstance();
        Singleton2 sl32 = Singleton2.getInstance();
        System.out.println(sl31 == sl32);

        Singleton3 sl11 = Singleton3.getInstance();
        Singleton3 sl12 = Singleton3.getInstance();
        System.out.println(sl11 == sl12);

    }
}
