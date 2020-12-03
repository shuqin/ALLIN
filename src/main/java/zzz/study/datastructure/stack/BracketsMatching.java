package zzz.study.datastructure.stack;

public class BracketsMatching {

    public static void main(String[] args) {
        test(null, false);
        test("", true);
        test("(", false);
        test(")", false);
        test("){", false);
        test("())", false);
        test("()", true);
        test("()[]{}", true);
        test("[]{}", true);
        test("[[{}]]", true);
        test("()([[{}]]}{}", false);
        test("()[[[{}]]}{}", false);
        test("([)]", false);
        test("([)]", false);
        test("([])", true);
        test("([({})])", true);
        test("(([]){})", true);


    }

    public static void test(String s, boolean isValid) {
        assert isValid(s) == isValid;
        assert isValid2(s) == isValid;
    }

    // 无法通过 test("(([]){})", false); 如果中间不匹配的话
    public static boolean isValid2(String s) {
        if (s == null) { return false; }
        if (s.length()== 0) { return true; }
        return isValidRec(s, 0, s.length()-1);
    }

    public static boolean isValidRec(String s, int startIdx, int endIdx) {
        int length = endIdx - startIdx + 1;
        if (length== 0) { return true; }
        if (length % 2 == 1) { return false; }

        int high = startIdx + (length >> 1);
        int low = high-1;

        while (low < high) {
            if (isMatch(s, low, high)) {
                low--;
                high++;
                if (low < startIdx) {
                    return true;
                }
            }
            else {
                break;
            }
        }
        if (isStartBracket(s.charAt(low)) || isEndBracket(s.charAt(high))) {
            return false;
        }
        if (low == startIdx && high == length) {
            return false;
        }
        if (low == startIdx && isEndBracket(s.charAt(low))) {
            return false;
        }
        if (high == endIdx && isStartBracket(s.charAt(high))) {
            return false;
        }
        return isValidRec(s, startIdx, low) && isValidRec(s, high, endIdx);
    }

    public static boolean isValid(String s) {
        if (s == null) { return false; }
        int length = s.length();
        if (length== 0) { return true; }
        if (length % 2 == 1) { return false; }

        char[] stack = new char[s.length()];
        int idx = 0;

        for (int i=0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (isStartBracket(c)) {
                stack[idx] = c;
                idx++;
            }
            else if (isEndBracket(c)){
                if (idx == 0) { return false; }
                char cs = stack[--idx];
                if (!isMatch(cs, c)) {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        return idx == 0;
    }

    public static boolean isStartBracket(char c) {
        return c == '(' || c == '[' || c == '{';
    }

    public static boolean isEndBracket(char c) {
        return c == ')' || c == ']' || c == '}';
    }

    public static boolean isMatch(String s, int i, int j) {
        return isMatch(s.charAt(i), s.charAt(j));
    }

    public static boolean isMatch(char c1, char c2) {
        return (c1 == '(' && c2 == ')') || (c1 == '[' && c2 == ']') || (c1 == '{' && c2 == '}');
    }
}
