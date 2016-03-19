package zzz.study.foundations.basic;

public class AutoBoxTrap {
	
	public void equalTest(Integer i1, Integer i2)
	{
		System.out.println(i1 + " " + (i1 == i2 ? " == " : " != ") + " " + i2);
	}
	
	public static void main(String[] args)
	{
		AutoBoxTrap abt = new AutoBoxTrap();
				
		Integer i1 = 127;
		Integer i2 = 127;
		abt.equalTest(i1, i2);		
		
		Integer i3 = 128;
		Integer i4 = 128;
		abt.equalTest(i3, i4);
			
		Integer i5 = -128;
		Integer i6 = -128;
		abt.equalTest(i5, i6);
		
		Integer i7 = -129;
		Integer i8 = -129;
		abt.equalTest(i7, i8);
		
		
		Integer i9 = 100;
		Integer i10 = 100;
		abt.equalTest(i9, i10);
		
		Integer i11 = new Integer(100);
		Integer i12 = new Integer(100);
		abt.equalTest(i11, i12);
	}

}
