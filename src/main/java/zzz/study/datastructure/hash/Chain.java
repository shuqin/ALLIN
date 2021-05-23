package zzz.study.datastructure.hash;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import shared.utils.ArrayUtil;

public class Chain {
	
	private static final String NONWORD = " ";   // 非词字符，用于启动程序输入和输出终止符
	private static final int NPREF = 2;          // 规定前缀词表中的单词数目
	private static final int LINE_CHARS = 30;    // 可读随机文本输出的一行最大字符数
	
	private static Random rand = new Random(47);
	
	private OutputStream oStream = null;
	
	public Chain() {
		oStream = System.out;
	}
	
	public Chain(OutputStream osStream) {
		this.oStream = osStream;
	}
	
	// 马尔可夫状态集合： 每一个前缀词表与相应所有后缀成为一个马尔可夫状态
	private Map<Prefix, List<String>> markovMap = new HashMap<Prefix, List<String>>();
		
	/**
	 * 构造马尔可夫状态集合
	 * @param is
	 * @throws IOException 
	 */
	public void build(InputStream is) throws IOException 
	{
		oStream.write("构建前缀词表-后缀的散列表： ".getBytes());
		String[] prefixWords = getInitPrefixWords();
		
		Scanner scanner = new Scanner(is);
		scanner.useDelimiter("\\s+");
		String word = "";
	
		Prefix prefix = new Prefix(prefixWords);
	    while (scanner.hasNext()) {
	    	word = scanner.next();
	    	add(prefix, word);	
	    	ArrayUtil.shift(prefixWords, word);
	    	prefix = new Prefix(prefixWords);
	    }
	    add(prefix, NONWORD);
	}
	
	/**
	 * 根据马尔可夫状态集合生成可读的随机文本
	 * @param nwords 指定输出单词数目
	 * @throws IOException 
	 */
	public void generate(int nwords) throws IOException
	{
		oStream.write("生成可读随机文本输出： ".getBytes());
		int count = 1;
		String[] prefixWords = getInitPrefixWords();
		Prefix prefix = new Prefix(prefixWords);
		
		List<String> suffix = null;
		String word = "";
		for (int i = 0; i < nwords; i++) {
			suffix =  markovMap.get(prefix);
			int index = Math.abs(rand.nextInt()) % suffix.size();
			word = suffix.get(index);
			if (word.equals(NONWORD)) {
				break;
			}
			oStream.write(word.getBytes());
			oStream.write(((count % LINE_CHARS == 0) ? '\n': ' '));
			count++;
			ArrayUtil.shift(prefixWords, word);
			prefix = new Prefix(prefixWords);
		}
		
	}
	
	// 输出马尔可夫状态集合
	public void outputMap() throws IOException
	{
		oStream.write("输出马尔可夫状态集合： ".getBytes());
		oStream.write('\n');
		Set<Map.Entry<Prefix, List<String>>> mapEntries = markovMap.entrySet();
		Iterator iter = mapEntries.iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Entry) iter.next();
			Prefix prefix = (Prefix) entry.getKey();
			List<String> suf = (List<String>) entry.getValue();
			oStream.write(prefix.toString().getBytes());
			oStream.write(" ---> ".getBytes());
			oStream.write(suf.toString().getBytes());
			oStream.write('\n');
		}
	}
	
	// 添加马尔可夫状态
	private void add(Prefix prefix, String word)
	{
		List<String> suffix =  markovMap.get(prefix);
		if (suffix == null) {
			suffix = new ArrayList<String>();
		}
		suffix.add(word);
		markovMap.put(prefix, suffix);	
	}
	
	// 初始化前缀词表为指定数目的非单词
	private String[] getInitPrefixWords()
	{
		String[] prefixWords = new String[NPREF];
		for (int i=0; i < NPREF; i++) {
			prefixWords[i] = NONWORD;
		}
		return prefixWords;
	}	


}
