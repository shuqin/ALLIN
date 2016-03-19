package zzz.study.patterns.proxy.dynamic;

public class DogIntercepter {

	public void methodOne() {
		System.out.println("拦截器方法（一）");
	}
	
	public void methodTwo() {
		System.out.println("拦截器方法（二）");
	}
	
	public void methodThree() {
		System.out.println("拦截器方法（三）");
	}
}
