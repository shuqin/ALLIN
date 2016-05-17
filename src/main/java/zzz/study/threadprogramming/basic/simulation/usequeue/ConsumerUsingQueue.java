/**
 * CharOutputThread:
 * 通过创建线程，并使用CharBuffer来实现并发地读和写字符缓冲区的仿真
 * 
 */

package zzz.study.threadprogramming.basic.simulation.usequeue;

import org.apache.log4j.Logger;
import zzz.study.threadprogramming.basic.simulation.TimeIndicator;

import java.util.concurrent.BlockingQueue;

public class ConsumerUsingQueue extends Thread {
	
	private static volatile boolean endflag = false;
	
	private final int id;
	
	private BlockingQueue<Character> buffer;

	private Logger log = Logger.getLogger("appInfo");
	
	public ConsumerUsingQueue(int id, BlockingQueue<Character> buffer) {
		this.id = id;
		this.buffer = buffer;
	}

	public static void cancel() {
		endflag = true;
	}
	
	public boolean isCanceled() {
		return endflag == true;
	}
	
	/**
	 * consume:	
	 * 当缓冲区buffer中有字符时，就取出字符显示【相当于消费者】。
	 * 
	 */
	public Character consume() throws InterruptedException {
		return buffer.take();

	}
	
	/**
	 * run:
	 * 一个被创建的任务，只要缓冲区不被置空，就从缓冲区中取出字符消费。
	 */
	public void run() {
					
		while (!isCanceled()) {
			try {
				log.info(TimeIndicator.getcurrTime() + ":\t" + this + " 取出字符: " + consume());
				log.info(TimeIndicator.getcurrTime() + ":\t" + this + " :\t\t\t" + buffer);
			} catch (InterruptedException e) {
				log.error(this + " Interrupted: " + e.getMessage());
			}
		}
	}
	
	public String toString() {
		return "C[" + id + "]";
	}
}
