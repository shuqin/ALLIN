package zzz.study.algorithm.dividing;

import java.util.List;
import java.util.stream.Collectors;

public class StringDividing {

    private static char[] chars = new char[] {
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
    };

    private static final Integer CHAR_LEN = 26;

    public static List<String> divide(int strlen, int n) {
        int maxValue = maxValue(strlen);
        List<Integer> ranges = Dividing.divideBy(maxValue, n);
        return ranges.stream().map(num -> int2str(num, strlen)).collect(Collectors.toList());
    }

    public static int maxValue(int m) {
        int multiply = 1;
        while (m>0) {
            multiply *= CHAR_LEN;
            m--;
        }
        return multiply - 1;
    }

    /**
     * 将整型转换为对应的字符串
     */
    private static String int2str(int num, int strlen) {
        if (num < CHAR_LEN) {
            return nchars('a', strlen-1) + chars[num];
        }
        StringBuilder s = new StringBuilder();
        while ( num >= CHAR_LEN) {
            int r = num % CHAR_LEN;
            num = num / CHAR_LEN;
            s.append(chars[r]);
        }
        s.append(chars[num % CHAR_LEN]);
        return s.reverse().toString() + nchars('a', strlen-s.length());
    }

    private static String nchars(char c, int n) {
        StringBuilder s = new StringBuilder();
        while (n > 0) {
            s.append(c);
            n--;
        }
        return s.toString();
    }

    public static void main(String[] args) {
        for (int len=1; len < 6; len++) {
            divide(len,8).forEach(
                    e -> System.out.println(e)
            );
        }

    }

}
