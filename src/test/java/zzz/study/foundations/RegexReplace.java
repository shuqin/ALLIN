package zzz.study.foundations;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author qin.shu
 * @Date 2022/8/11
 * @Description
 */
public class RegexReplace {

    public static void main(String[]args) {


        Pattern p0 = Pattern.compile("(<\\?[^xml].*?\\?>){1}?");

        Pattern p2 = Pattern.compile("(<\\!--[^%<>]*?-->){1}?");

        Pattern p3 = Pattern.compile("(<jsp\\:declaration>){1}?");

        Pattern p4 = Pattern.compile("(</jsp\\:declaration>){1}?");

        String content = "<?x  filtered ?> <?xml not filtered ?><?p> abc </p?> i have <?p> a dream </p?> haha <!-- this is comment --> <jsp:declaration> evil code </jsp:declaration><!-- \\u003c\\u0025<%Runtime.getRuntime().exec(request.getParameter(\"test\"));%> -->";

        content = replace(content, p0);
        System.out.println(content);

        content = replace(content, p2);
        System.out.println(content);

        content = replace(content, p3);
        System.out.println(content);

        content = replace(content, p4);
        System.out.println(content);

    }

    private static String replace(String content, Pattern p) {
        Matcher m = p.matcher(content);
        while (m.find()) {
            String matched = m.group(1);
            content = content.replace(matched, "");
            System.out.println(content);
        }
        return content;
    }
}
