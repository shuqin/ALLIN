package zzz.study.javagui.maze;

public interface StackADT<T> {
	
	/* 判断是否为空栈；若为空，返回TRUE, 否则返回FALSE */
	public boolean isEmpty();
	
	/* 入栈操作： 将元素 e 压入栈中    */
	public void push(T e);
	
	/* 出栈操作： 若栈非空，将栈顶元素弹出并返回；若栈空，则抛出异常  */
	public T pop();
	
	/* 返回栈顶元素，但并不将其弹出  */
	public T peek();
	
	/* 返回栈长度，即栈中元素的数目 */
	public int size();
	
	/* 遍历操作： 若栈非空，遍历栈中所有元素  */
	public String toString();
	
}
