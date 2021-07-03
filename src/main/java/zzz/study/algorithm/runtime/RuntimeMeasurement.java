package zzz.study.algorithm.runtime;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RuntimeMeasurement {

    // 问题最大规模: 以 10 的 size 次幂计
    private int maxsize;
    // 运行时间以 ms 计
    private double[] time;

    public RuntimeMeasurement(int maxsize) {
        this.maxsize = maxsize;
        time = new double[maxsize];
    }

    /**
     * measureTime : 对指定类型的对象调用指定参数列表的指定方法，并测量其运行时间
     *
     * @param type       指定对象类型，必须有一个 参数类型为 int 的公共构造器方法
     * @param methodName 指定测试方法名称，要求是空参数列表
     */
    public void measureTime(Class<?> type, String methodName) {
        try {
            Constructor<?> con = type.getConstructor(int.class);
            Method testMethod = null;
            for (int i = 0; i < time.length; i++) {
                Object obj = con.newInstance(power10(i + 1));
                testMethod = type.getMethod(methodName, new Class<?>[]{});
                long start = System.nanoTime();
                testMethod.invoke(obj, new Object[]{});
                long end = System.nanoTime();
                time[i] = ((end - start) / (double) 1000000);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    /**
     * showTime : 显示已经测量获得的运行时间，在 measureTime 方法调用后调用该方法。
     */
    public void showTime() {
        for (int i = 0; i < time.length; i++) {
            System.out.printf("n = %12d : ", power10(i + 1));
            System.out.printf("%12.3f\n", time[i]);
        }
    }

    private int power10(int n) {
        int result = 1;
        while (n > 0) {
            result *= 10;
            n--;
        }
        return result;
    }

}
