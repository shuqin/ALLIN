package zzz.study.foundations.enums;

import java.util.EnumMap;
import java.util.Map;

public class EnumMapDemo {

	public static void main(String[] args) {
		
		EnumMap<Fruit, String> em = new EnumMap<Fruit, String>(Fruit.class);
		em.put(Fruit.APPLE, "An apple a day keeps the doctor away.");
		em.put(Fruit.GRAPE, "Those who can't eat grape would say ... ");
		
		for (Map.Entry<Fruit, String> e: em.entrySet()) {
			System.out.println(e.getValue());
		}
	}
}
