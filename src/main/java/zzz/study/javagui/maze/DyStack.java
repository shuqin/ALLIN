package zzz.study.javagui.maze;

import java.util.ArrayList;

public class DyStack<T> implements StackADT<T> {
	
	private final int INIT_STACK_SIZE = 20;
	
	ArrayList<T> ds;        // 栈元素列表
	private int  top;       // 栈顶索引：当前栈顶元素的下一个元素的索引

	/* 
	 * 构造器：
	 * 使用默认容量创建一个空栈
	 * 
	 */
	public DyStack() {
		top = 0;
		ds = new ArrayList<T>(INIT_STACK_SIZE);
	}
	
	/* 
	 * 构造器：
	 * 使用指定容量创建一个空栈  
	 *
	 */
	public DyStack(int capacity) {
		 
		 top = 0;
		 ds = new ArrayList<T>(capacity);
	}
	
	public boolean isEmpty() {
		
		 if (top == 0)
			 return true;
		 else
			 return false;
	}
	
	public void  clear() {
		 ds.clear();
	}

	public void push(T e) {
		
		  ds.add(top, e);
		  top++;
	}

	public T pop() throws Exception	{
	      
		  if (ds.isEmpty()) 
		  	 throw new Exception("The stack has been empty!");
		  top--;
		  T result = ds.get(top);
		  ds.set(top, null);
		  return result;
	}
	
	public T peek() throws Exception {
		  
		  if (ds.isEmpty())
			  throw new Exception("The stack has been empty!");
		  
		  return ds.get(top-1);
	}
	
	public int size() {
		return  top;
	}

	public String toString() {
		  
		  StringBuilder content = new StringBuilder(" ");
		  for (int i=0; i<top; i++) {
			  content.append(" --> ");
			  content.append(ds.get(i));
		  }
		  return content.toString();
	}
	
	public int getTop() {
		return top;
	}

}


