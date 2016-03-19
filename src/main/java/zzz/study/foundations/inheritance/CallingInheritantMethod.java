package zzz.study.foundations.inheritance;

public class CallingInheritantMethod {
	
	public static void main(String[] args)
	{
		Base base = new Derived("derived");
		base.say();
		base.getClass().cast(base).say();
		((Base)base).say();	
	}

}


