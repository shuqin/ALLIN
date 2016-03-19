package zzz.study.foundations.basic;

public class IntegerTrap {
	
	private Integer intNum ;
	
	public IntegerTrap() { }
	
	public IntegerTrap(int intNum)
	{
		this.intNum = intNum;
	}
	
	public String toString()
	{
		//! intNum == 0 处 当 intNum == null 时， 会出现问题; 并且， == 是 引用比较还是数值比较 ?
		//! return " * " + (intNum == 0 ? "零值" : "非零") + " * "; 
		return " * " + (intNum == null ? " 空值 " : (intNum.intValue() == 0 ? " 零值 " : " 非零值 ")) + " * "; 
	}
	
	public static void main(String[] args)
	{
		IntegerTrap itTrap = new IntegerTrap();
		System.out.println(itTrap); 
		itTrap = new IntegerTrap(0);
		System.out.println(itTrap);
		itTrap = new IntegerTrap(5);
		System.out.println(itTrap);
	}

}
