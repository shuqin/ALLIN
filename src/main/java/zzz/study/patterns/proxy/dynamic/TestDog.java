package zzz.study.patterns.proxy.dynamic;

public class TestDog {

	public static void main(String[] args) {
		
		Doable target = new Dog();
		Doable dogProxy = null;
		
		Object proxy = ProxyFactory.getProxy(target);
		if (proxy instanceof Doable) {
			dogProxy = (Doable)proxy;
		}
		
		// 直接执行目标对象的方法
		System.out.println(" ------- 直接执行目标对象的方法 ----------- ");
		target.info();
		target.run();
		
		System.out.println();
		
		// 执行目标对象代理的方法 
		System.out.println(" ------- 执行目标对象代理的方法 ----------- ");
		dogProxy.info();
		dogProxy.run();

	}
}
