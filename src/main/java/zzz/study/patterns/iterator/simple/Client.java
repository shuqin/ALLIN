package zzz.study.patterns.iterator.simple;

import java.util.Iterator;

public class Client {
	
	public static void main(String[] args) {
		
		int[] empty = null;
		try {
		   ArrayWithIterator awi = new ArrayWithIterator(empty);
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
		
		empty = new int[0];
		try {
		   ArrayWithIterator awi = new ArrayWithIterator(empty);
		} catch (Exception ex) {
			System.err.println(ex.getLocalizedMessage());
			ex.printStackTrace();
		}
		
		int[] array = new int[] { 2, 3, 4, 5, 6, 7, 8, 9, 10};
		
		ArrayWithIterator awi = new ArrayWithIterator(array);
		
		// 外部迭代器
		// 前向迭代遍历
		Iterator iter = awi.getFrontIterator();
		while (iter.hasNext()) {
			System.out.printf(iter.next() + " ");
		}
		
		System.out.println();
		
		// 后向迭代遍历
	    iter = awi.getEndIterator();
	    while (iter.hasNext()) {
			System.out.printf(iter.next() + " ");
		}
	    
	    System.out.println();
	    
	    // 内部迭代器
	    awi.traverse();
		
	}

}
