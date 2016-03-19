package zzz.study.foundations.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralListRegex {
	
	private static String permittedChars = "[a-zA-Z0-9\\+\\-\\*\\/\\%]";
	private static String basicUnit = "[a-zA-Z0-9\\+\\-\\*\\/\\%\\(\\),]";
	private static Pattern basicPattern = Pattern.compile(permittedChars + "|" + permittedChars + "\\(" + permittedChars + "?," + permittedChars + "?\\)?");
	private static Pattern extendPattern = Pattern.compile(permittedChars + "\\(" + basicUnit + "*," + basicUnit + "*\\)");

	/**
	 * separatorIndex : 求解广义表表达式中分割左右子树的分隔符的位置
	 *                  由于这里使用逗号分割左右子树，则当且仅当其位置应当满足：
	 *                  在该分割符之前，左括号的数目恰好比右括号的数目多1. 
	 * @return  若存在满足条件的分隔符，则返回其位置；否则返回 -1
	 */
	private static int separatorIndex(String expression)
	{
		int leftBracketCounter=0, rightBacketCounter=0;
		int index = 0;
		for (; index < expression.length(); index++) {
			char ch = expression.charAt(index);
			if (ch == '(') {
				leftBracketCounter++;
			}
			else if (ch == ')') {
				rightBacketCounter++;
			}
			else if (ch == ',') {
				if (leftBracketCounter == rightBacketCounter+1) break;
			}
		}
		if (index < expression.length()) {
			return index;
		}
		return -1;
	}
	
	/**
	 * separate : 递归分割广义表表达式
	 * @param expression 给定的广义表表达式
	 */
	private static void separate(String expression)
	{
		System.out.println("expression: " + expression);
		if (expression.length() < 3) { return ; }
		Matcher matcher = extendPattern.matcher(expression);	
		if (matcher.matches()) {
		      int index = separatorIndex(expression);
		      if (index == -1) {
		    	  return ;
		      }
		      String leftEx = expression.substring(2, index);
		      System.out.println("leftEx: " + leftEx);
		      separate(leftEx);
		      if (index+2 < expression.length()) {
		          String rightEx = expression.substring(index+1, expression.length()-1);
		          System.out.println("rightEx: " + rightEx);
		          separate(rightEx);
		      }     
		}
		else {
			return ;
		}
	}
	
	public static void testBasic(String input)
	{
		System.out.println(input + " matches " + " ? " + basicPattern.matcher(input).matches());
	}
	
	
	public static void main(String[] args)
	{
		System.out.println("basicPattern: " + basicPattern);
		testBasic("A");
		testBasic("A(B,)");
		testBasic("A(,B)");
		testBasic("A(B,C)");
		testBasic("A(,)");
		
		System.out.println("**************** 分割字符串 ****************");
		
		System.out.println("第一个： ");
		separate("A(B(C,),D(,E))");
		System.out.println("下一个： ");
		separate("A(B(C,,D(,E(F,)))");
		
	}
	
}
