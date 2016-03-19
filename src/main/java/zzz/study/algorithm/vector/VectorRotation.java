/**
 * VectorRotation  @author shuqin1984 2011-04-18
 * <p>
 * 本程序主要实现四种向量旋转算法;
 * 向量旋转问题： 将给定 n 维向量最左边的 i 位依次移动到向量最右边.
 * 比如，[1,2,3,4,5] 左移 3 位， 变成 [4,5,1,2,3]
 * 数据结构与算法描述
 * 1. 向量使用数组来表示；
 * 2. 四种算法如前所述，分别基于"移动数组元素", "跳跃交换元素", "交换数组区域", "数组逆置" 四种思路。
 */

package zzz.study.algorithm.vector;

import java.util.Arrays;

public class VectorRotation {

    private VectorRotation() {
    }

    /**
     * leftShift4: 基于移动数组元素的思路实现向量旋转.
     */
    public static int[] leftShift4(int[] arr, int i) {
        int shiftBits = processParameters(arr, i);
        if (shiftBits == 0) {
            return arr;
        }

        int arrlen = arr.length;
        int[] temp = new int[shiftBits];
        for (int k = 0; k < shiftBits; k++) {
            temp[k] = arr[k];
        }
        for (int k = shiftBits; k < arrlen; k++) {
            arr[k - shiftBits] = arr[k];
        }
        for (int k = 0; k < shiftBits; k++) {
            arr[k + arrlen - shiftBits] = temp[k];
        }
        return arr;
    }

    /**
     * leftShift3 : 基于跳跃交换元素求解向量旋转问题
     */
    public static int[] leftShift3(int[] arr, int i) {
        int shiftBits = processParameters(arr, i);
        if (shiftBits == 0) {
            return arr;
        }
        int arrlen = arr.length;
        for (int k = 0; k < gcd(arr.length, shiftBits); k++) {
            int temp = arr[k];
            int foreIndex = k;
            int afterIndex = k + shiftBits;
            while (afterIndex != k) {
                arr[foreIndex] = arr[afterIndex];
                foreIndex = (foreIndex + shiftBits) % arrlen;
                afterIndex = (afterIndex + shiftBits) % arrlen;
            }
            arr[foreIndex] = temp;
        }
        return arr;
    }

    /*
     * gcd: 求给定两数的最大公约数
     */
    private static int gcd(int m, int n) {
        if (m < 0 || n < 0) {
            throw new IllegalArgumentException("参数错误，必须均是正整数!");
        }
        if (m % n == 0) {
            return n;
        }
        return gcd(n, m % n);
    }

    /**
     * leftShift2 : 基于交换数组区域求解向量旋转问题
     */
    public static int[] leftShift2(int[] arr, int i) {
        int shiftBits = processParameters(arr, i);
        if (shiftBits == 0) {
            return arr;
        }
        int beginIndex = 0;
        int endIndex = arr.length - 1;
        int varlength = endIndex - beginIndex + 1;
        while (true) {
            if (varlength == 2 * shiftBits) {  // AB -> BA ; 所要左移的位数正好是数组长度的一半，只要将数组左右等长区域交换即可
                exchange(arr, beginIndex, beginIndex + shiftBits, shiftBits);
                break;
            } else if (varlength > 2 * shiftBits) { // ABlBr -> BrBlA ; 所要左移的位数小于数组长度的一半，将右边区域分成两份，并将其右半部分与左边区域交换
                exchange(arr, beginIndex, varlength - shiftBits, shiftBits);
                endIndex -= shiftBits;
            } else if (varlength < 2 * shiftBits) { // AlArB -> BArAl ; 所要左移的位数大于数组长度的一半，将左边区域分成两份，并将其左半部分与右边区域交换
                exchange(arr, beginIndex, beginIndex + shiftBits, varlength - shiftBits);
                beginIndex += varlength - shiftBits;
                shiftBits = 2 * shiftBits - varlength;
            }
            varlength = endIndex - beginIndex + 1;
        }
        return arr;
    }

    /*
     * exchange: 将指定的数组区域内容互换，具体来说，
     * arr[beginIndex1:beginIndex1+length-1] 与 arr[beginIndex2, beginIndex2+length-1] 的内容依次互换，即
     * arr[beginIndex1] 与 arr[beginIndex2] 互换， ... arr[beginIndex1+length-1] 与 arr[beginIndex2+length-1] 互换.
     * 前置条件： 指定数组区域不能超过数组范围，且区域互不重叠
     *
     */
    private static void exchange(int[] arr, int beginIndex1, int beginIndex2, int length) {
        checkParametersForExchange(arr, beginIndex1, beginIndex2, length);
        for (int k = 0; k < length; k++) {
            int temp = arr[k + beginIndex1];
            arr[k + beginIndex1] = arr[k + beginIndex2];
            arr[k + beginIndex2] = temp;
        }
    }

    private static void checkParametersForExchange(int[] arr, int beginIndex1, int beginIndex2, int length) {
        if (beginIndex1 + length - 1 >= arr.length || beginIndex2 + length - 1 >= arr.length) {
            throw new IllegalArgumentException("参数错误，指定数组区域超过数组范围!");
        }
        if (Math.abs(beginIndex1 - beginIndex2) + 1 <= length)
            throw new IllegalArgumentException("参数错误，指定数组区域不能重叠!");
    }

    /**
     * leftShift: 基于数组逆置法求解向量旋转问题
     */
    public static int[] leftShift(int[] arr, int i) {
        int shiftBits = processParameters(arr, i);
        if (shiftBits == 0) {
            return arr;
        }
        reverse(arr, 0, shiftBits - 1);
        reverse(arr, shiftBits, arr.length - 1);
        reverse(arr, 0, arr.length - 1);
        return arr;
    }

    /**
     * reverse: 将数组的指定区域逆置
     * @param beginIndex 指定区域的起始下标
     * @param endIndex 指定区域的终止下标(包含)
     */
    public static void reverse(int[] arr, int beginIndex, int endIndex) {
        checkParameterForReverse(arr, beginIndex, endIndex);
        int length = endIndex - beginIndex + 1;
        for (int k = beginIndex; k < beginIndex + (length + 1) / 2; k++) {
            int temp = arr[k];
            arr[k] = arr[beginIndex + endIndex - k];
            arr[beginIndex + endIndex - k] = temp;
        }
    }

    private static void checkParameterForReverse(int[] arr, int beginIndex, int endIndex) {
        if (beginIndex < 0 || endIndex < 0 || beginIndex >= arr.length || endIndex >= arr.length) {
            throw new IllegalArgumentException("指定区域 [" + beginIndex + "," + endIndex + "] 错误, 参数必须均为正整数，且不能超过数组长度 " + arr.length);
        }
        if (beginIndex > endIndex) {
            throw new IllegalArgumentException("指定区域 [" + beginIndex + "," + endIndex + "] 错误，第一个参数必须不大于第二个参数!");
        }
    }

    /*
     * processParameters: 进行参数处理，若不合法，抛出异常；若合法，返回实际需要移位的位数
     */
    private static int processParameters(int[] arr, int i) {
        if (i < 0) {
            throw new IllegalArgumentException("参数错误，指定移位位数必须是正整数！");
        }
        int shiftBits = i % arr.length;
        return shiftBits;
    }

    static class Tester {

        public static void testLeftShift(int[] arr, int i) {
            System.out.println("将向量 " + Arrays.toString(arr) + " 循环左移 " + i + " 位：\t");
            try {
                int[] copy = Arrays.copyOf(arr, arr.length);
                System.out.println(Arrays.toString(leftShift(copy, i)) + " \t*** leftShift ");
                copy = Arrays.copyOf(arr, arr.length);
                System.out.println(Arrays.toString(leftShift2(copy, i)) + " \t*** leftShift2 ");
                copy = Arrays.copyOf(arr, arr.length);
                System.out.println(Arrays.toString(leftShift3(copy, i)) + " \t*** leftShift3 ");
                copy = Arrays.copyOf(arr, arr.length);
                System.out.println(Arrays.toString(leftShift4(copy, i)) + " \t*** leftShift4 ");

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        public static void testExchange() {
            int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
            for (int i = 1; i <= 6; i++) {
                int[] copy = Arrays.copyOf(arr, arr.length);
                try {
                    exchange(copy, 2, 5, i);
                    System.out.println("i = " + i + "\t" + Arrays.toString(copy));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        public static void testReverse() {
            int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
            for (int i = 0; i < arr.length; i++) {
                int[] copy = Arrays.copyOf(arr, arr.length);
                try {
                    reverse(copy, i, arr.length - i);
                    System.out.println("i = " + i + "\t" + Arrays.toString(copy));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        public static void testGCD() {
            int n = 200, m = 100;
            while (n >= -10 && m >= -10) {
                try {
                    System.out.println("[" + n + "," + m + "] 的最大公约数是 ：" + gcd(m, n));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                n -= 5;
                m -= 3;
            }
        }

        public static void main(String[] args) {
            System.out.println("************* 最大公约数 **************");
            testGCD();

            System.out.println("************* 数组区域内容交换 ****************");
            testExchange();

            System.out.println("************* 数组逆置 ****************");
            testReverse();

            System.out.println("************* 向量旋转 ****************");

            testLeftShift(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 3);
            testLeftShift(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 4);
            testLeftShift(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 8);
            testLeftShift(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 13);
            testLeftShift(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 30);
            testLeftShift(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 0);
            testLeftShift(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, -1);

        }
    }

}
