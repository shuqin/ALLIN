package zzz.study.patterns.dutychain.simple;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Dispatcher {
	
	private List parses = new LinkedList();
	
	/*
	 * 添加解析器
	 */
	public void addParser(Parseable p) {
		parses.add(p);
	}
	
	/*
	 * 使用解析器责任链来处理输入
	 */
	public boolean handle(String input)
	{
		System.out.println("input: " + input);
		Iterator iter = parses.iterator();
		while (iter.hasNext()) {
			Parseable p = (Parseable)iter.next();
			return p.parse(input);
		}
		return false;
	}
	
	/*
	 * 创建默认责任链
	 */
	public void defaultDispatch()
	{
		
	}

}
