package zzz.study.datastructure.hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import shared.utils.ArrayUtil;


public class Prefix {
	
	// 前缀词表
	private String[] prefWords = null;
	
	public Prefix(String[] prefWords)
	{
		this.prefWords = prefWords.clone();
	}
	
	
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Prefix)) {
			return false;
		}
		Prefix prefix = (Prefix) obj;
		for (int i=0; i < prefWords.length; i++) {
			if (!prefWords[i].equals(prefix.prefWords[i])) {
				return false;
			}
		}
		return true;
	}
	
	public int hashCode()
	{
		int result = 17;
		for (int i=0; i < prefWords.length; i++) {
			result = result * 37 + prefWords[i].hashCode();
		}
		return result;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder("");
		sb.append('(');
		for (String word: prefWords) {			
			sb.append(word);
			sb.append(' ');
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(')');
		return sb.toString();
	}

	
	public static void main(String[] args)
	{
		for (int prefixWordNum = 2; prefixWordNum < 4; prefixWordNum++) {
			System.out.println("前缀词表数目： " + prefixWordNum);
			String[] words =  "I have a dream , a dream that one day i could ...".split(" ");
			
			String[] prefixWords = new String[prefixWordNum];
			for (int i=0; i < prefixWords.length; i++) {
				prefixWords[i] = " ";
			}
			
			List<Prefix> prefixList = new ArrayList<Prefix>();
			Prefix prefix = new Prefix(prefixWords);
			prefixList.add(prefix);
			
			for (String word: words) {
				ArrayUtil.shift(prefixWords, word);
				System.out.println(Arrays.toString(prefixWords));
				prefixList.add(new Prefix(prefixWords));
			}
			System.out.println("前缀列表: " + prefixList);
		}
	}
}
