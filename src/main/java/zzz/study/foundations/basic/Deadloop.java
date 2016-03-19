package zzz.study.foundations.basic;

/**
 * 分析 cpu 问题
 */
public class Deadloop {

    public static void main(String[] args) {
        while (true) {
            int x = 1 + 1;
        }
    }
}
