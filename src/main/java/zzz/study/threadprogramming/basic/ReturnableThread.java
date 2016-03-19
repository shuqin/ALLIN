package zzz.study.threadprogramming.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ReturnableThread implements Callable<Integer> {

	
	private int givenNum;
	
	public ReturnableThread(int givenNum)
	{
		this.givenNum = givenNum;
	}
	
	/**
	 * 计算第  n 个 斐波那契数
	 */
	public int fabo(int n)
	{
		if (n == 1 || n == 2) return 1;
		else return fabo(n-1)+fabo(n-2);
	}
	
	/**
	 * 计算前 n 个 斐波那契数之和
	 */
	public Integer call() throws Exception {
		
		int sum = 0;
		for (int i=1; i <= givenNum; i++) {
			sum += fabo(i);
		}	
		return sum;
	}
	
	public static void main(String[] args)
	{
		ExecutorService es = Executors.newCachedThreadPool();
		List<Future<Integer>> results = new ArrayList<Future<Integer>>();
		for (int i=1; i<=10; i++) {
			results.add(es.submit(new ReturnableThread(i)));
		}
		es.shutdown();
		for (Future<Integer> result: results) {
			try {
				System.out.println(result.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} 
		}
		
	}
	

}


/*
 * Note: 
 * 
 * 创建具有返回值的任务：
 * 
 * ①   实现 Callable<T> 接口，并指定参数类型 T ;
 * 
 * ②   实现方法： public T call() , 其中 T 是已指定的具体类型。
 * 
 * ③   创建线程，并使用 ExecutorService.submit(Callable<T> task) 来执行；
 * 
 * ④   创建  Future<T> 来存储执行结果。
 * 
 * ⑤  使用 get()方法获得执行结果。
 * 
 * 
 * 
 */
