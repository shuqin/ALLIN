package zzz.study.foundations.puzzlers;

import java.math.BigDecimal;

public class UnPrecision {
		
		public static void main(String args[]){
			System.out.println(2.00 - 1.10);
			System.out.println((2.00 - 1.10) * 100);
			System.out.println((2.00*100 - 1.10 * 100) / 100);
			System.out.println((2*100 - 1.10 * 100) / 100);
			System.out.println(new BigDecimal("2.00").subtract(new BigDecimal("1.10")));
			System.out.printf("%7.3f", 2.00-1.10);
		}

}

/*
 *  Note:
 *  
 *  进行数值计算时，要牢记计算机无法精确地表示很多实数。
 * 
 * 
 *  打印结果：0.8999999999999999， 而不是 0.90
 *  在需要精确答案的地方，要避免使用float和double；
 *  对于货币计算，要使用int、long或BigDecimal。
 *  
 *  System.out.printf("%7.3f", 2.00-1.10); 
 *  虽然能打印出正确结果，但无法掩盖底层的计算不精确性。
 *  
 */
