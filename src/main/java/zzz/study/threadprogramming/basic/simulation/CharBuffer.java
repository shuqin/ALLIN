/**
 * CharBuffer:
 * 实现有限长度字符缓冲区的互斥读写。
 * 
 */

package zzz.study.threadprogramming.basic.simulation;

public class CharBuffer {
	
	private final int capacity;       // 指定字符缓冲区能容纳的字符数
	
	private char[] charBuffer;   // 用来生产和消费的有限长度字符缓冲区
	private int  index;

	private int  count;          // 该缓冲区被读写的次数,可衡量性能
	
	public CharBuffer(int capacity) {
		
		if (charBuffer == null) {
			charBuffer = new char[capacity];
		}
		this.capacity = capacity;
		index = 0;
		
	}
	
	/**
	 * 判断缓冲区是否已满，满则生产者等待
	 */
	public boolean isFull()
	{
		return index == capacity;
	}
	
	/**
	 * 判断缓冲区是否为空，空则消费者等待
	 */
	public boolean isEmpty()
	{
		return index == 0;
	}
	
	/**
	 * write: 将给定字符写入缓冲区中【改变了缓冲区内容】
	 * synchronized 关键字用于实现互斥访问缓冲区
	 * @param ch character that will be written into the buffer.
	 * 
	 */
	public synchronized void write(char ch) {
		
	      charBuffer[index] = ch;
	      index++;     
	      count++;
	}
	
	/**
	 * read: 读取缓冲区中给定位置的字符【不改变缓冲区内容】
	 * synchronized 关键字用于实现互斥访问缓冲区
	 * @param index integer representation of the position 
	 * 
	 */
	public synchronized char read(int index) {
		  return charBuffer[index]; 
	}
	
	/**
	 * fetch: 取出缓冲区给定位置的字符【改变了缓冲区内容】
	 * synchronized 关键字用于实现互斥访问缓冲区
	 *
	 */
	public synchronized char fetch() {  

		index--;
		count++;
		return charBuffer[index];	
	}
	
	/**
	 * getStringOfBuffer: 缓冲区内容的字符串表示
	 * @return  string representation of the buffer's contents
	 * 
	 */
    public synchronized String toString() {
		
		if (isEmpty()) {
			return "缓冲区为空！";
		}
		else {
			StringBuilder bufferstr = new StringBuilder("缓冲区内容： ");		
			for (int i=0; i < index; i++) {
				bufferstr.append(charBuffer[i]);
			}
		return bufferstr.toString();
		}
		
	}

	public int getCount() {
		return count;
	}

}
