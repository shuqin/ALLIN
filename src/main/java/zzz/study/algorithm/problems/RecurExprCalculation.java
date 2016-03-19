package zzz.study.algorithm.problems;

public class RecurExprCalculation {
	
	private RecurExprCalculation() {
		
	}
	
	/**
	 * 计算递推式： 
	 * T(n,k) = T(n-1, k-1) + T(n-1, k) + 1
	 * T(n,n) = T(n,0) = 0
	 * @throws Exception 
	 */
	public static long calc2RecurExpr(int n, int k) throws Exception
	{
		if (n < 0 || k < 0 || n < k)
			throw new Exception("参数错误，参数必须均为正整数，并且第一个参数值不小于地第二个参数的值");
		if (n == k || k == 0)
			return 0;
		else {
			return calc2RecurExpr(n-1, k-1) + calc2RecurExpr(n-1, k) + 1;
		}
	}
	
	public static void main(String[] args)
	{
		try {
			for (int n = 0; n < 20; n++) {
				for (int k = 0; k <= n; k++)
					System.out.println("T(" + n + "," + k + ") = " + calc2RecurExpr(n, k));
			    System.out.println();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
