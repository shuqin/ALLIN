/**
 * Sqrt: 计算给定正整数的平方根
 * <p>
 * 首先必须明确：A. 大多数时候只能得到近似解； B. 指定精度
 * <p>
 * 有三种方法：
 * 1. 使用级数求和公式
 * <p>
 * <p>
 * 2. 上下限试探法
 * 算法基本思路：不断试探，小则升，大则降，类似二分搜索。
 * 算法步骤如下：
 * (1) 令 low = high = 0；
 * (2) A. 若 high^2 < n , 则 high++; 转至 (2) ;
 * B. 若 high^2 = n ，则返回 high; 转至 (5) ;
 * C. 若 high^2 > n, 转至(3) ;
 * (3) low = high-1; mid = (low + high) / 2 ； 转至 (4) ;
 * (4) A. 若 mid^2 落在 [n-error, n+error] ， 则返回 mid; 转至(5) ；
 * B. 若 mid^2 落在 [0, n-error) , 则 low = mid; mid = (low + high) / 2 ；转至 (4);
 * C. 若 mid^2 落在 (n+error, +NAN)， 则取 high=mid; mid = (low + high) / 2 ; 转至 (4);
 * (5) 算法结束。
 * <p>
 * 3. 牛顿法
 * X(n+1) = (X(n) + D/X(n)) / 2;
 * X(0) = (1+D)/2 ; D 是给定的要计算平方根的数
 */

package zzz.study.algorithm.problems;

public class Sqrt {

    /**
     * 计算给定正整数 n 的平方根，误差为 error ; 返回给定正整数的平方根 。
     * 使用上下限试探法
     */
    public static double mysqrt(int n, double error) {
        if (n == 0) return 0;
        if (n < 0) throw new IllegalArgumentException("求平方根的数必须是正整数!");
        if (error < 0) throw new IllegalArgumentException("误差必须是正数!");
        double high = 1.0;
        while (true) {
            double uplimit = high * high;
            if (uplimit == n) {
                return high;
            } else if (uplimit < n) {
                high++;
            } else {
                break;
            }
        }

        double low = high - 1;
        double mid = (low + high) / 2;
        while (true) {
            double uplimit = mid * mid;
            if (n - uplimit > error) {
                low = mid;
                mid = (low + high) / 2;
            } else if (uplimit - n > error) {
                high = mid;
                mid = (low + high) / 2;
            } else {
                return mid;
            }

        }

    }

}
