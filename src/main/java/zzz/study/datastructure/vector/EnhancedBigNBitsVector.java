package zzz.study.datastructure.vector;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现 n 维位向量 *** 增强版
 * n 可以为相当大的数值, 使用整形数组的数组的所有位来串联成一个位数不小于 n 的位向量
 */
public class EnhancedBigNBitsVector {

    private static final int BITS_PER_INT = 32;
    private static final int INTS_PER_ARRAY = 1024;
    private static final int BITS_PER_ARRAY = BITS_PER_INT * INTS_PER_ARRAY;

    private static final int SHIFT = 5;

    // 将一个整型数组的数组中的所有整数的位串联成一个位向量
    private List<int[]> bitsVector;

    // 位向量的总位数
    private long bitsLength;

    public EnhancedBigNBitsVector(long n) {
        long intNums = (n % BITS_PER_INT == 0) ? (n / BITS_PER_INT) : (n / BITS_PER_INT + 1);
        long arrNums = (intNums % INTS_PER_ARRAY == 0) ? (intNums / INTS_PER_ARRAY) : (intNums / INTS_PER_ARRAY + 1);
        this.bitsLength = BITS_PER_INT * INTS_PER_ARRAY * arrNums;
        if (bitsVector == null) {
            bitsVector = new ArrayList<int[]>();
            for (int i=0; i< arrNums; i++) {
                bitsVector.add(new int[INTS_PER_ARRAY]);
            }
        }
    }

    /**
     * setBit: 将位向量的第 i 位置一
     *
     * @param i 要置位的位置
     */
    public void setBit(int i) {
        int arrIndex = i / BITS_PER_ARRAY;
        int intBits = i - arrIndex * BITS_PER_ARRAY;
        int intIndex = intBits / BITS_PER_INT;
        int[] arr = bitsVector.get(arrIndex);
        arr[intIndex] |= 1 << (intBits & 0x1f);

    }

    /**
     * clrBit: 将位向量的第 i 位清零
     *
     * @param i 要清零的位置
     */
    public void clrBit(int i) {
        int arrIndex = i / BITS_PER_ARRAY;
        int intBits = i - arrIndex * BITS_PER_ARRAY;
        int intIndex = intBits / BITS_PER_INT;
        int[] arr = bitsVector.get(arrIndex);
        arr[intIndex] &= ~(1 << (intBits & 0x1f));
    }

    /**
     * testBit: 测试位向量的第 i 位是否为 1
     *
     * @param i 测试位的位置
     * @return 若位向量的第 i 位为 1, 则返回true, 否则返回 false
     */
    public boolean testBit(int i) {
        int arrIndex = i / BITS_PER_ARRAY;
        int intBits = i - arrIndex * BITS_PER_ARRAY;
        int intIndex = intBits / BITS_PER_INT;
        int[] arr = bitsVector.get(arrIndex);
        return (arr[intIndex] & 1 << (intBits & 0x1f)) != 0;
    }


    /**
     * clr: 位向量全部清零
     */
    public void clr() {
        int vecLen = bitsVector.size();
        for (int i = 0; i < vecLen; i++) {
            bitsVector.set(i, new int[INTS_PER_ARRAY]);

        }
    }

    /**
     * getBitsLength: 获取位向量的总位数
     */
    public long getBitsLength() {
        return bitsLength;
    }

    /**
     * 获取给定整数 i 的二进制表示， 若高位若不为 1 则补零。
     *
     * @param i 给定整数 i
     */
    public String intToBinaryStringWithHighZero(int i) {
        String basicResult = Integer.toBinaryString(i);
        int bitsForZero = BITS_PER_INT - basicResult.length();
        StringBuilder sb = new StringBuilder("");
        while (bitsForZero-- > 0) {
            sb.append('0');
        }
        sb.append(basicResult);
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("");
        for (int i=bitsVector.size()-1; i>=0; i--) {
            int[] arr = bitsVector.get(i);
            for (int j = arr.length - 1; j >= 0; j--) {
                sb.append(intToBinaryStringWithHighZero(arr[j]));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        EnhancedBigNBitsVector nbitsVector = new EnhancedBigNBitsVector(Integer.MAX_VALUE);
        nbitsVector.setBit(2);
        System.out.println(nbitsVector);
        nbitsVector.setBit(7);
        nbitsVector.setBit(18);
        nbitsVector.setBit(25);
        nbitsVector.setBit(36);
        nbitsVector.setBit(49);
        nbitsVector.setBit(52);
        nbitsVector.setBit(63);
        System.out.println(nbitsVector);
        nbitsVector.clrBit(36);
        nbitsVector.clrBit(35);
        System.out.println(nbitsVector);
        System.out.println("52: " + nbitsVector.testBit(52));
        System.out.println("42: " + nbitsVector.testBit(42));
        nbitsVector.clr();
        System.out.println(nbitsVector);
    }

}
