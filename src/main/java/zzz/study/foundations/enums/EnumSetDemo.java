package zzz.study.foundations.enums;

import java.util.EnumSet;

public class EnumSetDemo {
	
	public static void main(String[] args) {
		
		EnumSet<Fruit> fes = EnumSet.of(Fruit.APPLE, Fruit.GRAPE) ;
		System.out.println(fes);
		
		fes.add(Fruit.BANANA);
		fes.add(Fruit.APPLE);
		fes.add(Fruit.WATERMELON);
		System.out.println(fes);
		
		fes.clear();
		System.out.println(fes);
		
		fes.addAll(EnumSet.allOf(Fruit.class));
		System.out.println(fes);
		
		fes = EnumSet.range(Fruit.APPLE, Fruit.GRAPE);
		System.out.println(fes);
		
		fes = EnumSet.complementOf(fes);
		System.out.println(fes);
		
		EnumSet<Fruit> copyOffes = EnumSet.copyOf(fes);
		System.out.println(fes);
	}

}
