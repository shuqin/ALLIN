package zzz.study.foundations.string;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import shared.utils.MapUtil;


public class TextUtil {
	
	private TextUtil() {
		
	}
	
	private static Pattern pattern = Pattern.compile("[^\n]*\n?");
	
	private static Pattern wordPattern = Pattern.compile("[\\w\\-]+");
	
	/**
	 * statisticWord: 统计文本的单词及其数目
	 * @param text 给定文本
	 * @return 给定文本中的单词及其数目的映射集合
	 */
	public static Map<String, Integer> statisticWord(String text)
	{	
		Map<String, Integer> wordsMap = new HashMap<String, Integer>();
		Matcher matcher = wordPattern.matcher(text);
		while (matcher.find()) {
			String word = matcher.group();
			int count = (wordsMap.get(word) == null) ? 1 : wordsMap.get(word)+1;
			wordsMap.put(matcher.group(), count);
		}
		return wordsMap;		
	}
	
	
	/**
	 * compareText: 比较给定的两个多行文本，并给出比较信息
	 * @param text1 给定文本1
	 * @param text2 给定文本2
	 */
	public static void compareText(String text1, String text2)
	{
		int lineCount = 1;
		Matcher matcher1 = pattern.matcher(text1);
		Matcher matcher2 = pattern.matcher(text2);
		String line1 = "";
		String line2 = "";
		boolean flag1 = false;
		boolean flag2 = false;
		while (true) {
			flag1 = matcher1.find();
			flag2 = matcher2.find();
			if (!flag1 && !flag2) { break; }
			line1 = flag1 ? matcher1.group().trim() : " Empty line";
			line2 = flag2 ? matcher2.group().trim() : " Empty line";
			if (!line1.equals(line2)) {
				System.out.printf("Line " + lineCount + " Not equal: \t");
				System.out.println(" line1 = " + line1 + " \t***\t line2 = " + line2);
			}
			lineCount++;
			flag1 = false;
			flag2 = false;
		}
		
	}
	
	/**
	 * compareFile : 比较两个文本文件，并给出详细的比较信息
	 * @param file1 给定文本文件1
	 * @param file2 给定文本文件2
	 * @throws IOException 若读取文件失败，则抛出异常
	 */
	public static void compareFile(File textfile1, File textfile2) throws IOException  
	{
			compareText(readText(textfile1), readText(textfile2));
	}
	
	/**
	 * readText: 从给定文本文件中读取数据，并将文件内容保存到字符串中
	 * @param file 给定文本文件
	 * @return 若读取成功，则返回包含文本文件内容的字符串；否则，抛出异常
	 * @throws IOException 若读取文件失败，则抛出异常。
	 */
	public static String readText(File file) throws IOException 
	{
		BufferedReader fReader = new BufferedReader(new InputStreamReader(new FileInputStream(file) , "utf-8"));
		String text = "";
		StringBuilder result = new StringBuilder(); 
		try {
			while ((text = fReader.readLine()) != null) {
				result.append(text);
				result.append('\n');
			} 
		} finally {
			fReader.close();
		}
		return result.toString();		
	}
	
	/**
	 * writeText: 将指定文本写入到指定文本文件中
	 * @param text 指定文本
	 * @param file 指定文本文件
	 * @throws IOException 若写入文件失败，则抛出异常。
	 */
	public static void writeText(String text, File file) throws IOException
	{
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
		try {
		   bw.write(text);
		} finally {
			bw.close();
		}
	}
	
	/**
	 * capitalFirst: 将给定字符串的首字母大写
	 * @param s 给定字符串
	 * @return 首字母大写后的字符串
	 */
	public static String capitalFirst(String s)
	{
		char first = Character.toUpperCase(s.charAt(0));
		return first + s.substring(1);
	}
	
	/**
	 * multiReplace: 用给定的字符串集合中的每一个字符串分别对给定文本中匹配给定正则表达式的部分进行替换
	 * @param text 给定文本
	 * @param regex 给定正则表达式
	 * @param replaces 用于替换的字符串集合
	 * @return 用给定的字符串集合中的每一个字符串分别对匹配给定正则表达式的部分进行替换后的字符串
	 */
	public static String multiReplace(String text, String regex, String[] replaces)
	{
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		StringBuilder sb = new StringBuilder();
		for (String str: replaces) {
				sb.append(m.replaceAll(str));
				sb.append(' ');
		}
		return sb.toString();
	}
	
	/**
	 * 将给定文本中匹配正则表达式的部分用给定字符串替换，并返回替换后的字符串
	 * @param text  给定文本
	 * @param regex 给定正则表达式
	 * @param replacement 用来替换匹配部分的字符串
	 * @return 替换后的字符串
	 */
	public static String replaceAll(String text, String regex, String replacement)
	{
		Matcher matcher = Pattern.compile(regex).matcher(text);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, replacement);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	
	static class Tester {
		
		public static void main(String[] args) {
		   
			try {
				compareFile(new File("/home/shuqin1984/1"), new File("/home/shuqin1984/2"));
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			System.out.println("************************************");
			
			String text1 = "i have a little dream \n " + 
			               "that i want to have \n" +
			               " a clean house  \n " +
			               "in which i could heartily inspired to \n" +
			               " learn , to work, and to relax.";
			String text2 = "i have a little dream \n " + 
	        "that i wanna  have \n" +
	        " a clean and beautifule house  \n " +
	        "in which i could heartily inspired to \n" +
	        " learn , to work, and to relax.\n" +
	        " a mi tuo fu.";
			
			compareText(text1, text2);
			
			
			PrintStream temPrintStream = System.out;
			
			try {
				PrintStream pStream = new PrintStream(new FileOutputStream(new File("./src/foundations/string/wordStatistics.txt"))); 
		        System.setOut(pStream);		
				
				System.out.println("****************** 单词统计 *****************");
				//! MapUtil.printMap(statisticWord(readText(new File("TextUtil.java"))));  // failed to read
				Map<String, Integer> wordsMap = statisticWord(readText(new File("./src/foundations/string/TextUtil.java")));
				MapUtil.print(MapUtil.sortMap(wordsMap));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.setOut(temPrintStream);
			
			System.out.println(capitalFirst("name"));
			System.out.println(capitalFirst("propertyName"));
			System.out.println(capitalFirst("2name"));
			
			System.out.println("dxx".replaceAll("x*", "ear"));
			System.out.println("多重替换: " + multiReplace("dxx", "xx", new String[] {"ear", "esc", "pp"}));
			

			String text = "my little dream, my little dream is \n" +
			             " to have a clean and beautiful house in which \n" + 
			             " I could be heartily inspired to work , to learn and to relax.\n";
			
			System.out.println("text before being replaced:\n " + text);
			System.out.println("text after having been replaced:\n " + replaceAll(text, "ea", "XXX"));
			
			
		}
	}

}
