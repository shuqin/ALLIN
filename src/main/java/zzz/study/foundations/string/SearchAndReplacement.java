package zzz.study.foundations.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchAndReplacement {

    public static void main(String[] args) {
        String[] strs = new String[]{
                "the tea is originated from Korea ? ",
                "this area is easily developed.",
                "the breakfast is very tasty.",
                "do not wear this dreadful dress."
        };
        Pattern eaPattern = Pattern.compile("(ea)[rskd]");
        Matcher matcher = eaPattern.matcher("");
        System.out.println("捕获组数: " + matcher.groupCount());
        System.out.println("匹配模式： " + eaPattern);
        for (String s : strs) {
            matcher.reset(s);
            System.out.println("待匹配的字符串： " + s);
            System.out.println("匹配结果： ");
            StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
                for (int j = 0; j <= matcher.groupCount(); j++) {
                    System.out.println(matcher.group(j) + " " + "start index: " + matcher.start()
                            + " " + "end index: " + matcher.end());
                }
                matcher.appendReplacement(sb, "XXX");
            }
            matcher.appendTail(sb);
            System.out.println("替换后的结果： " + sb);
            System.out.println("----------------------------------------");
        }

    }

}
