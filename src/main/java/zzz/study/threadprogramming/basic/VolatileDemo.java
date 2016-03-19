package zzz.study.threadprogramming.basic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import zzz.study.threadprogramming.basic.simulation.TimeIndicator;

public class VolatileDemo implements Runnable {
	
	private static boolean cancel = false;
	
	private final int id;
	
	public VolatileDemo(int id) {
		this.id = id;
	}

	public static void cancel() {
		cancel = true;
	}
	
	public boolean isCanceled() {
		return cancel == true;
	}
	
	public void run()
	{
		while (!isCanceled()) {
			System.out.println(TimeIndicator.getcurrTime() + ":\t" + this + " : rounding.");
		}
		System.out.println(TimeIndicator.getcurrTime() + ":\t" + this + " : quit from Volatile.");
	}
	
	public String toString() {
		return "Thread[" + id +"]";
	}
	
	public static void main(String[] args) {
		
        ExecutorService es = Executors.newCachedThreadPool();
		
		for (int i=0; i<10; i++) {
			es.execute(new VolatileDemo(i));
		}
		
		VolatileDemo.cancel();
		System.out.println(TimeIndicator.getcurrTime() + ":\t" + " cancel command has been delivered.");
		System.out.println("quit from main");
	}
	
	

}
