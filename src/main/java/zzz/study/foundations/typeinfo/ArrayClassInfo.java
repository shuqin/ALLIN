package zzz.study.foundations.typeinfo;

public class ArrayClassInfo {

    public static void main(String[] args) {

        boolean[] boolArray = new boolean[]{true, false};
        char[] charArray = new char[]{1, 2, 3, 4, 5};
        short[] shortArray = new short[]{1, 2, 3, 4, 5};
        int[] intArray = new int[]{1, 2, 3, 4, 5};
        long[] longArray = new long[]{1, 2, 3, 4, 5};
        double[] doubleArray = new double[]{1, 2, 3, 4, 5};

        Integer[] integerArray = new Integer[]{1, 2, 3, 4, 5};
        Double[] dArray = new Double[]{1.0, 2.0, 3.0, 4.0, 5.0};
        Object[] objArray = new Object[]{Integer.valueOf(0), Double.valueOf(0.0)};

        testClass(boolArray.getClass());
        testClass(charArray.getClass());
        testClass(shortArray.getClass());
        testClass(intArray.getClass());
        testClass(longArray.getClass());
        testClass(doubleArray.getClass());
        testClass(integerArray.getClass());
        testClass(dArray.getClass());
        testClass(objArray.getClass());

    }

    public static void testClass(Class<?> type) {
        System.out.println("类的全称 : " + type.getCanonicalName());
        System.out.println("类的名称 : " + type.getName());
        System.out.println("是否数组 ？ " + type.isArray());
        System.out.println("类加载器 : " + type.getClassLoader());
    }

}
