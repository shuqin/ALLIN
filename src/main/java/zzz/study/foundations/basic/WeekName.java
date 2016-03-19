package zzz.study.foundations.basic;

import java.util.Iterator;

public class WeekName implements Iterable<String> {
	
	private final String[] weekName = new String[] {
		"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" 	
	};
	
	public Iterator<String> iterator() {
		return new Iterator<String>() {

			int index = 0;
			public boolean hasNext() {
				return index >= 0 && index < weekName.length;
			}

			public String next() {
				return weekName[index++];
			}

			public void remove() {
				throw new UnsupportedOperationException();
				
			}
			  
		};
	}

}

/*
 * NOTE:
 * 要使用 foreach 循环遍历自定义集合或对象， 就必须实现 Iterable<T> 接口，实现 iterator 方法。 
 * next: 可能抛出 NoSuchElementException 异常， 此异常继承 RuntimeException  
 * remove: 可能抛出 UnSupportedOperationException 或 IllegalStateException 异常， 此异常继承 RuntimeException
 */
