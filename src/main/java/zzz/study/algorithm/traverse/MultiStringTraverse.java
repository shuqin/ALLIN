package zzz.study.algorithm.traverse;

public class MultiStringTraverse {

    public static void main(String[] args) {
        test(new String[] {"flow", "flake", "flight"}, "fl");
        test(new String[] {"ab", "a"}, "a");
        test(new String[] {"", "a"}, "");
        test(new String[] {"", null}, "");
    }

    public static void test(String[] strs, String expect) {
        assert MultiStringTraverse.longestCommonPrefix(strs).equals(expect);
    }

    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) { return ""; }
        if (strs.length == 1) { return strs[0]; }
        for (int p=0; p < strs.length; p++) {
            if (strs[p] == null) {
                return "";
            }
        }

        int pos=0;
        int strp=1;
        boolean isNotEquals = false;
        while (pos < strs[0].length()) {
            for (;strp < strs.length; strp++) {
                if (pos >= strs[strp].length() || strs[0].charAt(pos) != strs[strp].charAt(pos)) {
                    isNotEquals = true;
                    break;
                }
            }
            if (isNotEquals) {
                break;
            }
            pos++;
            strp=1;
        }
        if (pos == 0) { return ""; }
        return strs[0].substring(0,pos);
    }
}
