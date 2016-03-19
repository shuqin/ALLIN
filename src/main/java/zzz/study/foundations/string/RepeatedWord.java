package zzz.study.foundations.string;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RepeatedWord: 在给定文本中查找重复的单词
 * 单词定义：
 * 1. 单词字符包括大小写英文字母以及连字符、单引号;
 * 2. 若连字符或单引号出现在单词中，则只能在字母序列中间；
 * 3. 单词是由单词字符组成的序列，单词之间由空格符或标点符号隔开;
 * 4. 英文字母序列的中间带连字符也算一个单词;
 * 5. 上一行换行符之前的字符序列末尾若有连字符，则与下一行开头的第一个非单词字符前的字符序列合起来算作一个单词；连字符不计入单词中.
 * 
 * @author shuqin1984 2011-02-10
 *
 */

public class RepeatedWord {
	
	private static final String wordRegex = "([a-zA-Z]+[-']?[a-zA-Z]+|[a-zA-Z])(?:[,.;?!]?)";
	private static final String separatorRegex = "(?:\\s+|[,.;?!]\\s*)";
	private static final String wholeRegex = wordRegex + separatorRegex + wordRegex + separatorRegex;
	 
	/**
	 * repeatedWord: 从指定文本文件中读取每一行输入，并检测是否有重复单词；
	 * 若有重复单词，则去除重复单词（只保留其中一个），最后将去除后的结果写入指定文件中
	 * @param inputFile   指定输入文本文件，从中读取每一行输入
	 * @param outputFile  指定输出文本文件，将去除重复单词后的最终结果写入该文件中
	 * @throws IOException
	 */
	public static void repeatedWord(String inputFile, String outputFile) throws IOException
	{
		
		Pattern repeatedpat = Pattern.compile(wholeRegex);		
		Matcher matcher = repeatedpat.matcher("");
		System.out.println("groups: " + matcher.groupCount());
		System.out.println("********************************************");
		
		BufferedReader fileReader = new BufferedReader(new FileReader(new File(inputFile)));
		BufferedWriter fileWriter = new BufferedWriter(new FileWriter(new File(outputFile)));
		
		String content = "";
		String repeatedWord = "";
		int lineNO = 0;
		
		while ((content = fileReader.readLine()) != null) {
			
			lineNO++;
			matcher.reset(content + "\n");
			StringBuilder repeatedDeleted = new StringBuilder("");
			
			int searchStartIndex = 0;
			while (matcher.find(searchStartIndex)) {
				repeatedWord = matcher.group(1);
				// System.out.println(matcher.group(1) + " -> " + matcher.group(2)); // 用于调试
				if (repeatedWord.equals(matcher.group(2))) {
					System.out.printf("Line %-3d:  word '%s' repeated.\t", lineNO, repeatedWord);
					System.out.printf("Index: %3d   Repeated Index: %3d\n" , matcher.start(1) , matcher.start(2));
					
					
				}
				else {
					
					repeatedDeleted.append("");
				}
				
				// 重置搜索的起始位置为后面单词的首字母位置，从后面单词继续搜索
				searchStartIndex = matcher.start(2);
			}
			
			fileWriter.append(repeatedDeleted);
			fileWriter.newLine();
				
		}	
		fileReader.close();
		fileWriter.close();
	}
	
	public static void main(String[] args)
	{
		
		 try {	
			 
			RepeatedWord.repeatedWord("given.txt", "output.txt");
				
		 } catch (IOException e) {
			 System.out.println(e.getMessage());
			 e.printStackTrace();
		 } catch (Exception e) {
			 System.out.println(e.getMessage());
			 e.printStackTrace();
		}
	}

}
