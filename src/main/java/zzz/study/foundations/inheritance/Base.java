package zzz.study.foundations.inheritance;

public class Base {
	
    private int i;
	protected String name;
	
	public Base() {
		i = 0;
		name = "base";
		System.out.println("Base Constructor with no arguments. 0");
	}
	
	public Base(int i, String name) {
		System.out.println("Base Constructor with arguments. 1");
		this.i = i;
		this.name = name;
	}
	
	public void say() {
		System.out.println(" --- this is base class ! --- ");
	}
	
	public String toString()
	{
		return "(" + i + " " + name + ")";
	}

}
