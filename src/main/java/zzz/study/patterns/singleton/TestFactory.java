package zzz.study.patterns.singleton;

/*
 * 生成两个不同的全局工厂， 无法实现单例模式
 */
public class TestFactory extends Thread {

	public void run() {
		Factory  factory = Factory.getFactory();
		System.out.println("run(): " + factory);
	}
	
	public static void main(String[] args) {
		Thread tt = new TestFactory();
		tt.start();
		
		Factory factory = Factory.getFactory();
		System.out.println("main(): " + factory);
	}
}
