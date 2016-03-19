package zzz.study.foundations.puzzlers;

public class Type1 {
    public static void main(String[] args) {
        String s = null;
        System.out.println(s instanceof String);
    }
}

/*
 * Note:
 *
 * instanceof 操作符被定义为： 左操作数为 null 时， 返回 false.
 *
 *
 */
