package zzz.study.foundations.string;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUsage {
	
	private static final Pattern CLASS_PATTERN = Pattern.compile("\\w*.class");
	
    public static void main(String[] args) {
		
		testPatternMatches();
		testPatternSplit();
		multiMatch();
	}

	
	public static void testPatternMatches()
	{
		System.out.println(" ------------------------------------- ");
		System.out.println("使用 Pattern.matches() 方法进行便捷匹配： ");
		System.out.println("Pattern: ^\\w*.class  String: java.class: ");
		System.out.println("是否匹配？\t" + (Pattern.matches("^\\w*.class", "java.class") ? "Yes" : "No"));
		System.out.println("Pattern: ^\\w*.class  String: java.class$: ");
		System.out.println("是否匹配？\t" + (Pattern.matches("^\\w*.class", "java.class$") ? "Yes" : "No"));
		System.out.println(" ------------------------------------- ");
	
	}
	
	public static void testPatternSplit()
	{
		System.out.println(" ------------------------------------- ");
		System.out.println(" Pattern.split() 方法： ");
		Pattern p = Pattern.compile(".class");
		String text = "java.class[]BasicRegex.class[]xxx.class[]yyy.class";
		String[] splits = p.split(text);
		System.out.println("分割结果[]：" + Arrays.toString(splits));
		
		for (int i = -1; i < 8; i++) {
			splits = p.split(text, i);
			System.out.printf("分割结果[%d]： %s\n" , i, Arrays.toString(splits));	
		}

		System.out.println(" ------------------------------------- ");
		
	}
	
	public static void multiMatch()
	{
		System.out.println(" ------------------------------------- ");
		String  input = "java.class BasicRegex.class xxx.class yyy.class";
		System.out.println("text = " + input + "\t*** text length: " + input.length());
		System.out.println("正则表达式： " + CLASS_PATTERN.pattern());
		System.out.println("匹配模式: " + CLASS_PATTERN);
			
		Matcher m = CLASS_PATTERN.matcher(input);
		
		System.out.println("******** 整个文本区域的匹配 *********");
		System.out.println("匹配器: " + m);	
		
		// 文本内的多次匹配, 不会改变匹配器的 lastmatch 状态
		while (m.find()) { 
			System.out.println("matched: " + m.group() + " \tPosition: " + m.start() + "-" + (m.end()-1));		
		}
		System.out.println("匹配器： " + m);  
		
		// 对整个输入串进行匹配
		System.out.println("matches() 整个字符串是否匹配 ？\t" + (m.matches() ? "Yes" : "No"));
		// 对输入串的起始部分进行匹配，可能会改变匹配器 lastmatch 的状态，影响后面的匹配
		System.out.println("lookingAt() 输入字符串的起始部分是否有匹配 ？\t" + (m.lookingAt() ? "Yes" : "No"));
		System.out.println("匹配器： " + m);  
		System.out.println("*********************************");
		
		System.out.println("********* 指定区域的匹配 ***********");
		m = m.region(12, 27);
		System.out.println("匹配器: " + m);
		System.out.println("matches() 整个字符串是否匹配 ？\t" + (m.matches() ? "Yes" : "No"));
		System.out.println("lookingAt() 输入字符串的前缀是否匹配 ？\t" + (m.lookingAt() ? "Yes" : "No"));
			
		System.out.println("*********************************");
		System.out.println("i have a dream that could make life easier.".replaceFirst("ea", "aa"));
		System.out.println("i have a dream that could make life easier.".replaceAll("ea", "aa"));
		System.out.println("*********************************");
	}
	
	
}
