package zzz.study.algorithm.permutation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *  Permutation:
 *  Generate all the permutations of a given number.
 *  
 *  生成 n 个数 的全排列。
 *
 */

public class Permutation {
	
    /** 
     * 每一个排列使用一个 LinkedList<Integer> 来存储,
     * 使用 LinkedList<Integer> 的 列表来存储所有的排列
     *  
     */
	
	private ArrayList<LinkedList<Integer>> perms;
	
	/**
	 *  使用 flag 作为交替 从左往右 和 从右往左 扫描的 标志 
	 *  这样，可以实现排列“最小变化”的要求。
	 *  即：每相邻的两个排列，只有两个相邻位置的不同。
	 * 
	 */
	
	private int flag = 1;
	
	
	/** 构造器  */
	
	public Permutation() {
		
		if (perms == null)
			perms = new ArrayList<LinkedList<Integer>>();
		
	}
	
	/** 生成 n 个数的全排列，并存储在 perms 中 */
	
	private void createPerm(int n) {
		
		if (n <= 0)
			throw new IllegalArgumentException();
		
		if (n == 1) {
			LinkedList<Integer> init = new LinkedList<Integer>();
			init.add(1);
			perms.add(init);
		}
		else {
			    createPerm(n-1);
			    
			    // 对每一个 n-1 的排列P(n-1), 将 n 插入到该排列 P(n-1) 的 n 个可能位置上，
			    // 即可得到 n 个 相应的 n 元素排列 P(n)
			    
			    int length = perms.size();
			    for (int i=0; i < length; i++) {
			       LinkedList<Integer> p = perms.get(i);
			       if (flag == 0) {
			    	   
			    	   // flag = 0: 从左向右扫描插入
			    	   
				       for (int j=0; j <= p.size(); j++) {
				    	  LinkedList<Integer> pcopy = copylist(p);
					      pcopy.add(j, n);
					      perms.add(pcopy);
					      flag = 1;
				       }
				       
			       }   
				    else {
				    	
				    	// flag = 1: 从右向左扫描插入
				    	
				    	for (int j=p.size(); j >=0; j--) {
					       LinkedList<Integer> pcopy = copylist(p);
						   pcopy.add(j, n);
						   perms.add(pcopy);
						   flag = 0;
					    }
				    	
				    }
			    } 
			    
			    // 删除所有的 P(n-1) 排列
			    
			    for (int i=0; i < length; i++) {
			    	 perms.remove(0);
			    }
	    
		}	
		
	}
	
	/** 获取 n 个元素的全排列  */
	
	public ArrayList<LinkedList<Integer>> getPerm(int n) {
		
		createPerm(n);	
		return perms;
	}

	
	/** 复制 list 的元素到另一个列表， 并返回该列表  */
	
	private LinkedList<Integer> copylist(LinkedList<Integer> list) {
		
		LinkedList<Integer> copylist = new LinkedList<Integer>();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Integer i = (Integer) iter.next();
			copylist.add(i);
		}
		return copylist;
	}

}
