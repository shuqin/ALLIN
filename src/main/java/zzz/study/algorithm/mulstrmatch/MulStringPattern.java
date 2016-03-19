package zzz.study.algorithm.mulstrmatch;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MulStringPattern {
	
	private static final String DEFAULT_DELIMITER = "/"; 
	private static final String UNIT_PATT = "[\\w\u4e00-\u9fa5]";
	
	private String[] strs;      // 模式中所含的字符串集合
	private String   separator; // 模式中的分隔符
	
	// 通过字符串形式表达的多字符串模式的正则表达式编译式
	private static Pattern p = Pattern.compile("^\\s*(?:[\\w\u4e00-\u9fa5]+([/:_])?)*\\s*$");
	
	/**
	 * 通过字符串形式来构造多字符串模式
	 * 字符串格式：[spaces][str1][/:_][str2][/:_]...[strn][spaces] 
	 */
	public MulStringPattern(String pattStr)
	{
		Matcher m = p.matcher(pattStr);
		if (m.find()) {
			separator = m.group(1);
			strs = pattStr.trim().split(separator);
		}
		else {
			strs = new String[0]; 
			separator = DEFAULT_DELIMITER; 
		}
	}
	
	
	public String getPattern()
	{
		return UNIT_PATT + "*" + cancat(UNIT_PATT + "*") + UNIT_PATT + "*";
	}
	
	public String toString()
	{
		return cancat(DEFAULT_DELIMITER);
	}
	
	private String cancat(String separator)
	{
		StringBuilder sbuilder = new StringBuilder();
		for (int i=0; i < strs.length; i++) {
			sbuilder.append(strs[i]);
			if(i != strs.length-1) { 
				sbuilder.append(separator); 
			} 
		}
		return sbuilder.toString();
	}
		
	public static void main(String[] args)
	{
		MulStringPattern msp = new MulStringPattern("A/C/E/G");
		System.out.println(msp.toString());
		msp = new MulStringPattern("A:B-CLSD");
		System.out.println(msp.toString());

	}

}
