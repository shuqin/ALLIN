package zzz.study.utils;

public class PrintUtil {

    private PrintUtil() {
    }

    /*
     * 打印  n 个 由  ch 指定的字符
     */
    public static void printNChars(char ch, int n) {
        if (n <= 0) {
            return;
        }
        while (n-- > 0) {
            System.out.print(ch);
        }
    }

    /**
     * 缩进  n 个空格
     */
    public static void indent(int n) {
        printNChars(' ', n);
    }

}
