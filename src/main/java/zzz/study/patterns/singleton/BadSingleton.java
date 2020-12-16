package zzz.study.patterns.singleton;

public class BadSingleton {

    public static final BadSingleton INSTANCE = new BadSingleton();

    public BadSingleton() {  // 诱惑写出错误代码
        if (INSTANCE != null) {
            throw new RuntimeException("此路不通");
        }
    }

}
