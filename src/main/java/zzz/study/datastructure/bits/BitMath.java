package zzz.study.datastructure.bits;

public class BitMath {

    // 绝对值，当取最小负整数时没有对应绝对值。
    public static int abs(int n) {
        return (n ^ (n >> 31)) - (n >> 31);
    }

    public static int max(int a, int b) {
        if ((a^b) > 0) {
            // 同符号时 a-b 不会溢出，避免错误
            return b & ((a-b) >> 31) | a & (~(a-b) >> 31);
        }
        return ((a ^ (1 << 31)) < 0) ? a : b;
    }

    public static int min(int a, int b) {
        if ((a^b) > 0) {
            // 同符号时 a-b 不会溢出，避免错误
            return a & ((a-b) >> 31) | b & (~(a-b) >> 31);
        }
        return ((a ^ (1 << 31)) >= 0) ? a : b;
    }

    // 位运算实现加法
    public static int add(int a, int b) {
        if(b==0)
            return a;
        int sum = a^b;
        int carry =(a&b)<<1;
        return add(sum,carry);
    }

    // 平均值
    public static int avg(int a, int b) {
        return ((a ^ b) >> 1) + (a & b);
    }

    // 判断正负，即判断最高位是 0 还是 1
    public static boolean isNotNegative(int x) {
        return (x ^ (1 << 31)) >= 0 ? false : true;
    }

    static class BitMathTester {
        public static void main(String[] args) {
            testAbs(0);
            testAbs(5);
            testAbs(-5);
            testAbs(Integer.MAX_VALUE);
            testAbs(Integer.MIN_VALUE);
            testAbs(Integer.MIN_VALUE+1);

            testMax(999,-999);
            testMax(999,0);
            testMax(0,-999);
            testMax(Integer.MAX_VALUE,Integer.MIN_VALUE);
            testMax(Integer.MAX_VALUE >> 1,Integer.MIN_VALUE >> 1);
            testMax(Integer.MAX_VALUE >> 1,(Integer.MIN_VALUE >> 1)-1);
            testMax((Integer.MAX_VALUE >> 1)+1,Integer.MIN_VALUE >> 1);


            testMin(999,-999);
            testMin(999, 0);
            testMin(0, -999);
            testMin(Integer.MAX_VALUE,Integer.MIN_VALUE);
            testMin(Integer.MAX_VALUE >> 1,Integer.MIN_VALUE >> 1);
            testMin(Integer.MAX_VALUE >> 1,(Integer.MIN_VALUE >> 1)-1);
            testMin((Integer.MAX_VALUE >> 1)+1,Integer.MIN_VALUE >> 1);
            testMin((Integer.MAX_VALUE >> 1)+2,(Integer.MIN_VALUE >> 1)-1);
            testMin((Integer.MAX_VALUE >> 1)+3,(Integer.MIN_VALUE >> 1)-2);

            testAdd(999, -1);
            testAdd(Integer.MAX_VALUE, Integer.MIN_VALUE);
            testAdd(Integer.MAX_VALUE/2, Integer.MAX_VALUE/2);
            testAdd(Integer.MAX_VALUE/2, Integer.MAX_VALUE/2+1);
            testAdd(Integer.MAX_VALUE/2+1, Integer.MAX_VALUE/2+1);
            testAdd(Integer.MIN_VALUE/2, Integer.MIN_VALUE/2);
            testAdd(Integer.MIN_VALUE/2, Integer.MIN_VALUE/2-1);

            testAvg(999, -1);
            testAvg(Integer.MAX_VALUE, Integer.MIN_VALUE);
            testAvg(Integer.MAX_VALUE/2, Integer.MAX_VALUE/2);
            testAvg(Integer.MAX_VALUE/2, Integer.MAX_VALUE/2+1);
            testAvg(Integer.MAX_VALUE/2+1, Integer.MAX_VALUE/2+1);
            testAvg(Integer.MIN_VALUE/2, Integer.MIN_VALUE/2);
            testAvg(Integer.MIN_VALUE/2, Integer.MIN_VALUE/2-1);



            testIsNotNegative(999);
            testIsNotNegative(-999);
            testIsNotNegative(-1);
            testIsNotNegative(1);
            testIsNotNegative(0);
            testIsNotNegative(Integer.MIN_VALUE);
            testIsNotNegative(Integer.MAX_VALUE);
        }

        public static void testAbs(int n) {
            System.out.println(String.format("abs(%d) = %d", n, abs(n)));
        }

        public static void testIsNotNegative(int n) {
            System.out.println(String.format("isNotNegative(%d) = %b", n, isNotNegative(n)));
        }

        public static void testMax(int m, int n) {
            System.out.println(String.format("max(%d,%d) = %d", m, n, max(m,n)));
        }

        public static void testMin(int m, int n) {
            System.out.println(String.format("min(%d,%d) = %d", m, n, min(m,n)));
        }

        public static void testAdd(int m, int n) {
            System.out.println(String.format("add(%d,%d) = %d", m, n, add(m, n)));
        }

        public static void testAvg(int m, int n) {
            System.out.println(String.format("avg(%d,%d) = %d", m, n, avg(m, n)));
        }
    }
}
