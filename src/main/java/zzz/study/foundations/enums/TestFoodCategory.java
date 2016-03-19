package zzz.study.foundations.enums;

public class TestFoodCategory {
	
	public static void main(String[] args) { 
		
		for (int i=0; i<=20; i++) {
			FoodCategory fc = Enums.random(FoodCategory.class);
			System.out.println("Category: " + fc);
			System.out.println("Food: " + fc.getRandomSelector().randomSelect());
		}
	}

}
