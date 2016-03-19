package zzz.study.datastructure.bits;

public class BitOp {

    // 取 n 的第 m 位
    public static int getBit(int n, int m) {
        return (n >> (m - 1)) & 1;
    }

    // 将 n 的第 m  位置 1
    public static int setBitToOne(int n, int m) {
        return n | (1 << (m - 1));
    }

    // 将 n 的第 m 位置 0
    public static int setBitToZero(int n, int m) {
        return n & ~(1 << (m - 1));
    }

    public static String toBinaryString(int n) {
        StringBuilder s = new StringBuilder();
        for (int i = 32; i > 0; i--) {
            s.append(getBit(n, i));
        }
        return s.toString();
    }

    public static void test(int n) {
        testUnit(n);

        int n1 = setBitToOne(n, 29);
        testUnit(n1);

        int n2 = setBitToZero(n, 6);
        testUnit(n2);
    }

    public static void testUnit(int num) {
        String standardBinaryStr = Integer.toBinaryString(num);
        System.out.println("Standard: " + standardBinaryStr);
        String myOwnBinaryStr = toBinaryString(num);
        System.out.println("My Own: " + myOwnBinaryStr);

        assert standardBinaryStr.equals(myOwnBinaryStr);
    }

    static class BitOpTester {
        public static void main(String[] args) {
            test(999);
            test(-999);
        }
    }
}
