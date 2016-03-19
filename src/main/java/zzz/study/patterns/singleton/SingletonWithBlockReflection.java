package zzz.study.patterns.singleton;

public class SingletonWithBlockReflection {

    public static final SingletonWithBlockReflection INSTANCE = new SingletonWithBlockReflection();

    private SingletonWithBlockReflection() {
        if (INSTANCE != null) {
            throw new RuntimeException("此路不通");
        }
    }

}
