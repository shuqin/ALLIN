package zzz.study.foundations.puzzlers;

public class LongDivision {
	
		public static void main(String args[]){
			final long MICROS_PER_DAY = 24 * 60 * 60 * 1000 * 1000;
			final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;
			System.out.println(MICROS_PER_DAY/MILLIS_PER_DAY);
		}

}

/*
 * 打印结果是5，而不是1000
 * 其原因在于： 计算 MICROS_PER_DAY 时， 右边表达式参与乘积运算的因子都是整型int，得到的结果也是整型int，
 * 在转化为long 之前，结果必须先放入int，再扩展为long，然而放入int时，由于结果太大而被截断。
 * 简而言之，该表达式实际上是：final long MICROS_PER_DAY = (long)((int)(24 * 60 * 60 * 1000 * 1000));
 * 
 * 
 * 更正：右边表达式的一个乘积因子声明为long，比如： 24L * 60 * 60 * 1000 * 1000
 * 教训：当你在操作很大的数字时，千万要提防溢出！
 * 
 */
