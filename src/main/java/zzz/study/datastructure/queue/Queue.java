package zzz.study.datastructure.queue;

public interface Queue<T> {
	
	/**
	 * isEmpty : 判断队列是否为空
	 */
	boolean isEmpty();
	
	/**
	 * size : 返回队列的长度
	 */
	int size();
	
	/**
	 * clear : 将队列清空
	 * @throws Exception 若清空操作失败，则抛出异常
	 */
	void clear() throws Exception;
	
	/**
	 * enqueue : 若队列未满，将元素入队
	 * @throws Exception 若入队失败，则抛出异常
	 * 
	 */
	void enqueue(T e) throws Exception;
	
	/**
	 * dequeue : 若队列不空，则将队列首元素出队，并返回
	 * @exception Exception 若队列为空，则抛出异常
	 */
	T dequeue() throws Exception;
	
	/**
	 * head : 若队列非空，则返回队列首元素；否则返回空值
	 */
	T head();
	
	/**
	 * tail : 若队列非空，则返回队列尾元素，否则返回空值
	 */
	T tail();
	

}
