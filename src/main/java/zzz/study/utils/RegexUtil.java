package zzz.study.utils;

import java.util.regex.Pattern;

public class RegexUtil {
	
	private RegexUtil() {}
	
	private static final Pattern POSITIVE_NUM_REGEX = Pattern.compile("\\s*(0|[1-9][0-9]*)\\s*");
	
	/*
	 * 判断传入的字符串是否为非负数， 可以允许 0 或任意正整数
	 */
	public static boolean isPositiveNumPattern(String text)
	{
		return POSITIVE_NUM_REGEX.matcher(text).matches();
	}

}
