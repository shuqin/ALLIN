package zzz.study.foundations.basic;

import java.util.ArrayList;
import java.util.Collection;

import zzz.study.foundations.sharingclasses.Dog;


public class IterableTester {
	
	public static<T> void testIterable(Iterable<T> iterable) {
		for (T ib: iterable) {
			System.out.println(ib);
		}
	}
	
	public static void main(String[] args) {
		
		Dog dog1 = new Dog("Big Huang");
		Dog dog2 = new Dog("GouGou");
		
		System.out.println(" ------ 数组 Foreach ----- ");
		/* 数组自动可进行 foreach 循环  */
		Dog[] dogs = new Dog[] { dog1, dog2};
		//! testIterable(dogs);
		//! 数组可以使用Foreach形式，但数组并不实现 Iterable 接口
		for (Dog dog: dogs) {
			System.out.println(dog.toString());
		}
		
		System.out.println(" ------ 集合 Foreach ----- ");
		Collection<Dog> dogList = new ArrayList<Dog>();
		dogList.add(dog1);
		dogList.add(dog2);
		// 集合类 Collection 及其子类、子接口均实现 Iterable 接口，因此可使用 Foreach 形式
		testIterable(dogList);
		
		/* 要使用 foreach 循环，必须是数组或者实现 Iterable 接口 */
		// DogList 实现了 Iterable 接口，因此可使用 Foreach 形式
		System.out.println(" ------ 自定义Iterable Foreach ----- ");
		WeekName wNames = new WeekName();
		System.out.println("week names: ");
		for (String wName: wNames) {
			System.out.printf(wName + " ");
		}

	}

}
