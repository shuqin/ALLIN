package zzz.study.threadprogramming.basic.dataconcurrency;

import zzz.study.algorithm.runtime.CommandRuntime;

public class PrimePrintRuntime {
	
	public static void main(String[] args) 
	{
		// 打印 n 以内的质数   单线程与多线程的运行时间比较
		long n = 10000000;
		CommandRuntime cr = new CommandRuntime(new PrimePrintInConcurrencyCommand(new PrimePrintInConcurrency(n, 20)));
		long runtimeInCon = cr.runtime();
		System.out.println("run time in concurrency: " + runtimeInCon + " ms.");
		
		CommandRuntime cr2 = new CommandRuntime(new PrimePrintSingleThreadCommand(new PrimePrintSingleThread(n)));
		long runtimeSingleThread = cr2.runtime();
		System.out.println("run time in single Thread: " + runtimeSingleThread + " ms.");
	}

}
