package zzz.study.foundations.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PunctionRegex {

    public static void main(String[] args) {
        String[] strings = new String[]{
                "word?", "don't?", "I am!", "GOd!", "you.", "a. ", "they;", " them;", "he,", "e,n", "fs",
        };
        Pattern p = Pattern.compile("[a-zA-Z]+[?!.,;]?");
        Matcher m = p.matcher("");
        for (String str : strings) {
            m.reset(str);
            if (m.matches()) {
                System.out.println(str + "\tmatches.");
                System.out.println("startIndex = " + m.start() + "\tendIndex = " + m.end());
            } else {
                System.out.println(str + "\tnot matched.");
            }
        }
    }

}
