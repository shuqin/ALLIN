package zzz.study.threadprogramming.basic;

public class CurrentThread implements Runnable {
	
	private int id;
	
	public CurrentThread(int id) {
		this.id = id;
	}
	
	public void run() {
		System.out.println(Thread.currentThread());
		System.out.println("quit from the current Thread");
	}
	
	public String toString() {
		return "Thread[" + id + "]";
	}
	
	public static void main(String[] args)
	{
		System.out.println("Enter into the main.");
		
		new Thread(new CurrentThread(5)).start();
		
		System.out.println(Thread.currentThread());
		System.out.println("quit from the current Thread");
	}

}
