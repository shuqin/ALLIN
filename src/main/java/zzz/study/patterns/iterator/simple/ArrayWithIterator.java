package zzz.study.patterns.iterator.simple;

import java.util.Iterator;

public class ArrayWithIterator {
	
	private int index;
	private int[] array;
	
	public ArrayWithIterator(int[] array) {
		if (array == null) {
			throw new NullPointerException("The array passed in should not be empty.");
		}
		this.array = array;
		index = 0;
	}
	
	/**
	 * 从前向后迭代的迭代器实现
	 * 
	 */
	Iterator getFrontIterator() 
	{
		index = 0;
		return new Iterator() {

			public boolean hasNext() {
				
				return index != array.length;
			}

			public Object next() {
				
				return array[index++];
			}

			public void remove() {
								
			}
			
		};
	}
		
		/**
		 * 从后向前的迭代器实现
		 * 	
		 */
		
		Iterator getEndIterator() 
		{	
			index = array.length;
			
			return new Iterator() {

				public boolean hasNext() {
					
					return (index>0);
				}

				public Object next() {
					
					return array[--index];
				}

				public void remove() {
					
				}
				
			};
		}
		
		public void traverse()
		{
			for (int i=0; i < array.length; i++) {
				System.out.printf("%d%c", array[i], (i%10==9) ? '\n':' ');
			}
		}
	

}
