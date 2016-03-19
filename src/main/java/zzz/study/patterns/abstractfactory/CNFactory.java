package zzz.study.patterns.abstractfactory;

/**
 * 具体工厂： 生产 CaCreditCheck 产品
 */
public class CNFactory implements AbstractFactory {

    private static CNFactory ca;
    private static Object classLock = CNFactory.class;

    private CNFactory() {

    }

    public static CNFactory getCNFactory() {
        synchronized (classLock) {
            if (ca == null) {
                ca = new CNFactory();
            }
        }
        return ca;
    }

    public IPad createIPad() {
        return new CNIPad();
    }

    public IMac createIMac() {
        return new CNIMac();
    }


}
