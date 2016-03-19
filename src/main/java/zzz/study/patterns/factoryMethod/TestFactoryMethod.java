package zzz.study.patterns.factoryMethod;

import java.util.Random;

public class TestFactoryMethod {
	
	public static void main(String[] args) {
		
		CreditCheck  cc = CreditCheckFactory.createCreditCheck();
		
		Random rand = new Random();
		int id = rand.nextInt(100000);
		double creditLimit = cc.creditLimit(id);
		System.out.println("客户ID号： " + id);
		System.out.println("客户赊购限额: " + creditLimit);
		
	}
	
}
