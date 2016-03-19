package zzz.study.threadprogramming.basic.dataconcurrency;


/**
 * 关于质数的工具类
 */
public class Prime {

    /**
     * 判断 n 是否为质数
     */
    public static boolean isPrime(long n) {
        if (n == 0 || n == 1) return false;
        double sqrt = Math.sqrt(n);
        for (int i = 2; i <= sqrt; i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }


}
