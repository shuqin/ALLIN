package zzz.study.patterns.singleton;

public class Singleton3 {

    private static Singleton3 singleton;

    private Singleton3() {
    }

    public static Singleton3 getInstance() {
        if (singleton == null) {  // 需要的时候才加载 Lazy
            singleton = new Singleton3();
        }
        return singleton;
    }
}
