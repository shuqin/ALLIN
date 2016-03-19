package zzz.study.foundations.generics;

/**
 * 演示泛型接口的实现
 */
public class CustomerManager implements Generator<Customer> {
	
	private static long count = 0;
	
	private static CustomerManager cm = new CustomerManager();
	
	private CustomerManager() { }
	
	public static CustomerManager getTheManager() {
		return cm;
	}
	
	public Customer next() {	
		return new Customer(++count);
	}
	
	public static void main(String[] args) {
		
		CustomerManager cm = CustomerManager.getTheManager();
		for (int i=0; i < 10; i++) {
			System.out.println(cm.next());
		}
	}

}

class Customer {
	private long id ;
	public Customer(long id) {
		this.id = id;
	}
	public String toString() {
		return "C[" + id + "]";
	}
}