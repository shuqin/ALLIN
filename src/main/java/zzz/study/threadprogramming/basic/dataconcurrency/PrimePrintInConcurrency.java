package zzz.study.threadprogramming.basic.dataconcurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 打印给定范围【1 - range】 的所有质数【使用多线程方式】
 * 
 */


public class PrimePrintInConcurrency {
	
	// 指定执行任务所需要的线程数
	private int threadNum = 20;
	
	// 指定筛选范围：[1,range)
	private long range;
	
	// 线程运行后的线程结果记录
	private List<Future<List<Long>>> results;
	
    // 筛选得到的最终质数列表
	private List<Long> primes;
	
	// 任务划分结果 ：
	// taskDivided[id][0] 表示 线程 id 开始执行的任务起点；
	// taskDivided[id][1] 表示线程 id 结束执行的任务终点。
	private static long[][] taskDivided;
	
	public PrimePrintInConcurrency(long range, int threadNum) 
	{
		if (primes == null) {
			primes = new ArrayList<Long>();
		}
		this.range = range;
		this.threadNum = threadNum;
	}
	
	/*
	 *  将 range 个任务 分配给  threadNum 个线程。
	 */
	private void divideTask()
	{
		taskDivided = new long[threadNum][2]; 
		long pieces = range / threadNum ;
		for (int id=0; id < threadNum-1; id++) {
			taskDivided[id][0] = id*pieces;
			taskDivided[id][1] = (id+1)*pieces;
		}
        taskDivided[threadNum-1][0] = (threadNum-1)*pieces;
	    taskDivided[threadNum-1][1] = range;
	}
	
	/*
	 * 使用多个线程并发地筛选质数
	 */
	private void filterPrimes()
	{	
		divideTask();
		clearLastResult();
		results = new ArrayList<Future<List<Long>>>();
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i=0; i<threadNum; i++) {
			results.add(exec.submit(new PrimePrintThread(i)));
		}
		exec.shutdown();
	}
	
	/*
	 * 清除前一次的线程运行结果
	 */
	private void clearLastResult()
	{
		if (primes != null && primes.size() > 0) {
			primes.clear();
		}
	}
	
	/*
	 * 将多个线程筛选的质数列表合并成最终的质数列表
	 */
	private void mergePrimes()
	{	
		for (Future<List<Long>> result: results) {
			try {
				primes.addAll(result.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}	
		}
	}
	
	/**
	 * 若进行了新的设置，并想获取或打印新的最终质数列表结果，必须先运行此方法【亦可直接运行方法 runAndPrint】。
	 * 
	 * 注意： 此方法运行一次后的结果会进行保存，并可调用方法  getPrimesList 或 printPrimes 获取或打印；
	 * 
	 * 如果只想获取或打印上一次运行的结果，就不必运行此方法。
	 * 
	 */
	public void run()
	{
		filterPrimes();
		mergePrimes();
	}
	
	/**
	 * 此方法专门用于获取最近一次运行所得到的最终质数列表
	 */
	public List getPrimesList()
	{
		return Collections.unmodifiableList(primes);
	}
	
	/**
	 * 此方法专门用于打印最近一次运行所得到的最终质数列表
	 */
	public void printPrimes()
	{
    	System.out.println("****** Print all the primes in range < " + range + " ******");
    	int count = 0;
    	for (long i: primes) {
    		System.out.printf(i + " ");
    		count++;
    		if (count % 10 == 0) 
    			System.out.println();
    	}
    	System.out.println("\n总共质数数目: " + primes.size());
	}
	
	/**
	 * 此方法用于在一次运行得到并打印最终质数列表
	 */
	public void runAndPrint()
	{
		run();
		printPrimes();
	}
	
	
	private class PrimePrintThread implements Callable<List<Long>> {
		
		private final int id;
			
		public PrimePrintThread(int id) {
			this.id = id;
		}
			
		public List<Long> call() {
			
			List<Long> primes = new ArrayList<Long>();
			
			for (long i=taskDivided[id][0];i<taskDivided[id][1];i++) {
				if (Prime.isPrime(i)) {
					primes.add(i);
				}
			}
			return Collections.unmodifiableList(primes);    
		}
			
	}

	
	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}

	public void setRange(long range) {
		this.range = range;
	}

	public static void main(String[] args)
	{
		long range = 98;
		PrimePrintInConcurrency ppic = new PrimePrintInConcurrency(range, 20);
		long start = System.currentTimeMillis();	
		ppic.runAndPrint();
    	long end = System.currentTimeMillis();
    	System.out.println("Eclipsed:" + (end-start) + " ms.");
    	
    	System.out.println("**** 直接获取最近一次运行的结果 ****");
    	ppic.printPrimes();
    	
    	System.out.println("**** 作新设置后运行的结果 ****");
    	
    	ppic.setRange(1000);
    	ppic.setThreadNum(10);
    	ppic.runAndPrint();
	}
}


/*
 * Note:
 * 多线程的两个用途：
 * 1. 将CPU计算与I/O操作相互隔离开来；
 * 2. 数据并行性计算任务，将大数据集计算分成多个子数据集计算任务
 * 
 * 本例演示使用多线程完成数据并行计算任务
 *
 */
