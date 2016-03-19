package zzz.study.patterns.singleton;

/*
 * 生成的是同一个工厂对象， 实现单例模式
 */
public class TestSynchronizedFactory extends Thread {

	public void run() {
		SynchronizedFactory  synchronizedFactory = SynchronizedFactory.getFactory();
		System.out.println("run(): " + synchronizedFactory);
	}
	
	public static void main(String[] args) {
		Thread tt = new TestSynchronizedFactory();
		tt.start();
		
		SynchronizedFactory  synchronizedFactory = SynchronizedFactory.getFactory();
		System.out.println("main(): " + synchronizedFactory);
	}
}
