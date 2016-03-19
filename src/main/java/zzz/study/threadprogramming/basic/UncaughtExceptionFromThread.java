package zzz.study.threadprogramming.basic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UncaughtExceptionFromThread implements Runnable {
	
	public void run()
	{
		throw new RuntimeException();
	}
	
	public static void main(String[] args)
	{
		// 使用 try-catch 语句块无法捕获线程中逃逸的异常。
		try {
			UncaughtExceptionFromThread et = new UncaughtExceptionFromThread();
			ExecutorService exec = Executors.newCachedThreadPool();
			exec.execute(et);
		}
		catch(Exception e) {
			System.err.println("Exception in thread has been caught here.");
		}
	}

}

/*
 * Note:
 * 
 * ①  使用通常的 try-catch 语句块无法捕获从线程中逃逸的异常。 
 * ② 最好直接在线程中处理异常。
 *
 */
