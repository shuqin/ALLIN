package zzz.study.foundations.puzzlers;

public class OddJudgement {

    public static boolean isOddNotGood(int i) {
        if (i >= 0) {
            return i % 2 == 1;
        } else {
            if (i == Integer.MIN_VALUE) return false;
            return (i + Integer.MAX_VALUE) % 2 == 0;
        }
    }

    public static boolean isOddWithPitfalls(int i) {
        return i % 2 == 1;
    }

    public static boolean isOdd(int i) {
        return i % 2 != 0;
    }

    public static boolean isOddUsingBitOp(int i) {
        return (i & 1) != 0;
    }


}

/*
 * Note:
 *
 * 关于数值计算问题，一定要考虑全面，将数值的各种情况都考虑在内。
 * 比如，整数： 正数，零，负数。
 *
 * isOddWithPitfalls 方法正是忽略了对负数情况的考虑才导致了bug.
 *
 */
