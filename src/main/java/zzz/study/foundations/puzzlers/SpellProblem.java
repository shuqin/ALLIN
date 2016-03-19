package zzz.study.foundations.puzzlers;

public class SpellProblem {
	
	public static void main(String[] args){
		System.out.println(12345+5432l);
	}

}

/*
 * 打印出来的是 17777，而不是 66666！
 * 
 * 注意： 1 和 l
 * 教训：在long型字面常量中，一定要用大写的L，千万不要用小写的l
 * 
 */
