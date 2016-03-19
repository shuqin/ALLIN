package zzz.study.threadprogramming.basic;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SleepThread implements Runnable {
	
	private int sleepTime;   // time unit: ms

	private int id;
	
	public SleepThread(int id, int sleepTime) {
		this.sleepTime = sleepTime;
		this.id = id;
	}
	
	public void sleep()
	{
		long start = System.currentTimeMillis();
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			System.err.println("Interrupted.");
		}
		long end = System.currentTimeMillis();	
		System.out.println( "Thread[" + id + "]" + "Eclipsed: " + (end-start) + " ms");
	}
	
	public void run()
	{
		sleep();
	}
	
	
	public static void main(String[] args) 
	{
		Random rand = new Random();
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i=0; i<20; i++) {
			exec.execute(new SleepThread(i, (rand.nextInt(10)+1)*1000));
		}
        exec.shutdown();
        
		System.out.println("执行器是否关闭？ " + exec.isShutdown());
		System.out.println("执行器中的所有任务是否已执行完毕？ " + exec.isTerminated());
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			System.err.println("Interrupted.");
		}
		exec.shutdownNow();
		try {
			if (!exec.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
				System.err.println("还有部分任务没有终结。");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("执行器是否关闭？ " + exec.isShutdown());
		System.out.println("执行器中的所有任务是否已执行完毕？ " + exec.isTerminated());
	}

}
