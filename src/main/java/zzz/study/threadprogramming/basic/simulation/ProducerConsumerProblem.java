/** 
 * TestThread ：
 * 
 * 使用主线程不断从键盘缓冲区获取输入，写入自创建的字符缓冲区，并显示缓冲区内容；
 * 使用一个子线程不断从自创建的字符缓冲区取出字符输出，并显示缓冲区内容； 
 * 
 */

package zzz.study.threadprogramming.basic.simulation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProducerConsumerProblem {
	
	public static void main(String[] args) {
		
		System.out.println(" ---- Thread main starts up ---- ");
		
		CharBuffer sharedBuffer = new CharBuffer(15);
		ExecutorService es = Executors.newCachedThreadPool();

		for (int i=1; i <= 10; i++) {
			es.execute(new Producer(i, sharedBuffer));
			es.execute(new Consumer(i, sharedBuffer));
		}
		es.shutdown();	
		try {
			TimeUnit.SECONDS.sleep(15);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Producer.cancel();
		Consumer.cancel();
		es.shutdownNow();
		
		System.out.println("Time to be over.");
		
	}

}
