package zzz.study.foundations.basic;

import java.util.concurrent.TimeUnit;

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
