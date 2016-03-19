package zzz.study.algorithm.dynamicplan;

import java.math.BigInteger;

/**
 * BinomialTheorem
 * 计算二项式系数及展开式
 */

public class BinomialTheorem {

    /**
     * 二项式的幂
     */
    private final int power;

    /**
     * 二项式展开式的系数向量，binomialCoeffs[i] 存储 C(power,i)
     */
    private BigInteger[] binomialCoeffs;

    // 标记： 如果已经计算过该对象的二项式系数向量，则不必再重新计算
    private boolean flag = false;

    public BinomialTheorem(int power) {
        this.power = power;
        if (binomialCoeffs == null) {
            binomialCoeffs = new BigInteger[power / 2 + 1];
        }
    }

    public static BinomialTheorem getInstance(int power) {
        if (power < 0) {
            throw new IllegalArgumentException("参数错误，指定二项式的幂必须为正整数！");
        }
        if (power < 256) {
            return BinoTheoremCache.cache[power];
        } else {
            return new BinomialTheorem(power);
        }
    }

    /**
     * combinNum : 计算组合数的便利方法
     *
     * @return 组合数 C(n,k)
     */
    public static BigInteger combinNum(int n, int k) {
        if (n < 0 || k < 0 || n < k)
            throw new IllegalArgumentException();
        int finalk = (k <= n / 2) ? k : (n - k);
        BigInteger[] coeffs = new BigInteger[finalk + 1];
        for (int i = 0; i < coeffs.length; i++) {
            coeffs[i] = BigInteger.valueOf(0);
        }
        calcBinomialCoeffs(coeffs, n);
        return coeffs[finalk];
    }

    /*
     * calcBinomialCoeff: 计算二项式展开的系数
     * C(n,k) = C(n,n-k)
     * C(n,k) = C(n-1,k) + C(n-1, k-1)
     * C(n,0) = C(n,n) = 1
     *
     */
    private static void calcBinomialCoeffs(BigInteger[] binomialCoeffs, int powerNum) {
        // binomialCoeffs : 存储 C(powerNum, j) = C(powerNum-1 , j-1) + C(powerNum-1, j)
        for (int i = 0; i <= powerNum; i++) {
            int upperIndex = Math.min(i / 2, binomialCoeffs.length - 1);
            for (int k = upperIndex; k >= 0; k--) {
                if (k == 0 || i == k)
                    binomialCoeffs[k] = BigInteger.valueOf(1);
                else if (2 * k == i) {
                    binomialCoeffs[k] = binomialCoeffs[k - 1].multiply(BigInteger.valueOf(2));
                } else {
                    binomialCoeffs[k] = binomialCoeffs[k - 1].add(binomialCoeffs[k]);
                }
            }
        }
    }

    /**
     * getBinomial : 获得二项式展开式的字符串表达形式
     * (a+b)^n = a^n + C(n,1)*a^(n-1)*b + ... + C(n,k)*a^(n-k)*b^k + C(n,n-1)*a*b^(n-1) + b^n
     */

    public String toString() {

        if (flag == false) {  // 计算二项式展开式的系数向量，且仅仅计算一次
            calcBinomialCoeffs();
        }

        if (power == 0) {
            return "(a+b)^0 = 1";
        }

        String beginString = "(a+b)" + "^" + power + " = \n";
        StringBuilder result = new StringBuilder(beginString);

        for (int i = 0; i <= power; i++) {

            int equivIndex = (i <= power / 2) ? i : (power - i);

            // 衔接二项式系数 C(n,i) : 若 C(n,i) = 1 则省略不显示; 若 i > power/2 , 则 i = power - power/2 .
            result.append((binomialCoeffs[equivIndex].compareTo(BigInteger.valueOf(1)) == 0) ? "" : binomialCoeffs[equivIndex]);
            result.append(displayTerm("a", power - i));  // 衔接 * a^(n-i)
            result.append(displayTerm("b", i)); // 衔接 * b^i
            result.append(" + ");
            if (i % 10 == 9) {
                result.append('\n');
            }
        }
        result.deleteCharAt(result.length() - 2);

        return result.toString();

    }

    /*
     * displayTerm : 显示二项式的项 term^power
     * 若 power = 0 ，则不显示该项 ； 若 power = 1, 则只显示 term
     * 若 power > 1 , 则显示 term^power*
     */
    private String displayTerm(String term, int power) {
        if (power == 0) {
            return "";
        }
        if (power == 1) {
            return term;
        }
        return "(" + term + "^" + power + ")";
    }

    /**
     * binomialCoeff: 计算二项式展开的系数 C(n,0) --- C(n,n)
     * C(n,k) = C(n,n-k)
     * C(n,k) = C(n-1,k) + C(n-1, k-1)
     * C(n,0) = C(n,n) = 1
     */
    public void calcBinomialCoeffs() {

        for (int i = 0; i < binomialCoeffs.length; i++) {
            binomialCoeffs[i] = BigInteger.valueOf(0);
        }
        calcBinomialCoeffs(this.binomialCoeffs, power);
        flag = true;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof BinomialTheorem)) {
            return false;
        }
        return ((BinomialTheorem) obj).power == power;
    }

    public int hashCode() {
        return power;
    }

    private static class BinoTheoremCache {

        private static BinomialTheorem[] cache = new BinomialTheorem[256];

        static {
            for (int i = 0; i < 256; i++) {
                cache[i] = new BinomialTheorem(i);
            }
        }

        private BinoTheoremCache() {
        }
    }

}
