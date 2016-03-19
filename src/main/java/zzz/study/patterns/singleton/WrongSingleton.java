package zzz.study.patterns.singleton;

public class WrongSingleton {

    public static final WrongSingleton INSTANCE = new WrongSingleton();

    static class WrongSingletonTest {
        public static void main(String[] args) {
            WrongSingleton wrongSingleton = new WrongSingleton();
            WrongSingleton wrongSingleton2 = new WrongSingleton();
            System.out.println(wrongSingleton == wrongSingleton2);
        }
    }

}
