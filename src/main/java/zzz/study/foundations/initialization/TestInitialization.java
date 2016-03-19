package zzz.study.foundations.initialization;

public class TestInitialization {
	
	public static void main(String[] args) {
		
		// 仅仅调用类的静态方法，而不创建类的对象
		System.out.println(" Call Static method. ");  
		StaticInitialization.f();
		System.out.println(" Call again.");
		StaticInitialization.f();
		
		System.out.println("****************************");
		
		// 创建类的对象   
		new StaticInitialization();
		System.out.println(" -------- Creating another. -------- ");
		new StaticInitialization();
	}

}
