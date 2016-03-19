package zzz.study.foundations.puzzlers;

import java.util.Calendar;

public class StaticInitProblem {
    public static final StaticInitProblem INSTANCE = new StaticInitProblem();
    private static final int CURRENT_YEAR =
            Calendar.getInstance().get(Calendar.YEAR);
    private final int beltSize;

    private StaticInitProblem() {
        beltSize = CURRENT_YEAR - 1930;
    }

    public static void main(String[] args) {
        System.out.println("Elvis wears a size " +
                INSTANCE.beltSize() + " belt.");
    }

    public int beltSize() {
        return beltSize;
    }
}

/*
 * Note:
 *
 * 对静态域的初始化依赖于对静态域的声明顺序。
 * 每一个初始化器都必须位于依赖于它的初始化器之前。
 *
 * 当心初始化循环。
 *
 *
 */
