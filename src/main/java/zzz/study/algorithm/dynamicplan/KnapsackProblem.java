
/**
 * 求解背包问题：
 * 给定 n 个背包，其重量分别为 w1,w2,……,wn, 价值分别为 v1,v2,……,vn
 * 要放入总承重为 totalWeight 的箱子中， 
 * 求可放入箱子的背包价值总和的最大值。
 * 
 * NOTE: 使用动态规划法求解 背包问题
 * 设 前 n 个背包，总承重为 j 的最优值为 v[n,j], 最优解背包组成为 b[n];
 * 求解最优值：
 * 1. 若 j < wn, 则 ： v[n,j] = v[n-1,j];
 * 2. 若  j >= wn, 则：v[n,j] = max{v[n-1,j], vn + v[n-1,j-wn]}。
 * 
 * 求解最优背包组成：
 * 1. 若 v[n,j] > v[n-1,j] 则 背包 n 被选择放入 b[n], 
 * 2. 接着求解前 n-1 个背包放入 j-wn 的总承重中， 
 *    于是应当判断 v[n-1, j-wn] VS v[n-2,j-wn], 决定 背包 n-1 是否被选择。
 * 3. 依次逆推，直至总承重为零。
 *    
 *    重点： 掌握使用动态规划法求解问题的分析方法和实现思想。
 *    分析方法： 问题实例 P(n) 的最优解S(n) 蕴含 问题实例 P(n-1) 的最优解S(n-1);
 *              在S(n-1)的基础上构造 S(n) 
 *    实现思想： 自底向上的迭代求解 和 基于记忆功能的自顶向下递归
 */

package zzz.study.algorithm.dynamicplan;

import java.util.ArrayList;

public class KnapsackProblem {
	
	/** 指定背包 */
	private Knapsack[] bags;
	
	/** 总承重  */
	private int totalWeight;
	
	/** 给定背包数量  */
	private int n;
	
	/** 前 n 个背包，总承重为 totalWeight 的最优值矩阵  */
	private int[][] bestValues;
	
	/** 前 n 个背包，总承重为 totalWeight 的最优值 */
	private int bestValue;
	
	/** 前 n 个背包，总承重为 totalWeight 的最优解的物品组成 */
	private ArrayList<Knapsack> bestSolution;
	
	public KnapsackProblem(Knapsack[] bags, int totalWeight) {
		this.bags = bags;
		this.totalWeight = totalWeight;
		this.n = bags.length;
		if (bestValues == null) {
			bestValues = new int[n+1][totalWeight+1];
		}
	}
	
	/**
	 * 求解前 n 个背包、给定总承重为 totalWeight 下的背包问题
	 * 
	 */
	public void solve() {
		
		// 求解最优值
		for (int j = 0; j <= totalWeight; j++) {
			for (int i = 0; i <= n; i++) {
			
				if (i == 0 || j == 0) {
					bestValues[i][j] = 0;
				}	
				else 
				{
					// 如果第 i 个背包重量大于总承重，则最优解存在于前 i-1 个背包中，
					// 注意：第 i 个背包是 bags[i-1]
					if (j < bags[i-1].getWeight()) {
						bestValues[i][j] = bestValues[i-1][j];
					}	
					else 
					{
						// 如果第 i 个背包不大于总承重，则最优解要么是包含第 i 个背包的最优解，
						// 要么是不包含第 i 个背包的最优解， 取两者最大值，这里采用了分类讨论法
						// 第 i 个背包的重量 iweight 和价值 ivalue
						int iweight = bags[i-1].getWeight();
						int ivalue = bags[i-1].getValue();
						bestValues[i][j] = 
							Math.max(bestValues[i-1][j], ivalue + bestValues[i-1][j-iweight]);		
					} // else
				} //else		 
		   } //for
		} //for
		
		// 求解背包组成
		if (bestSolution == null) {
			bestSolution = new ArrayList<Knapsack>();
		}
	    int tempWeight = totalWeight;
	    for (int i=n; i >= 1; i--) {
		   if (bestValues[i][tempWeight] > bestValues[i-1][tempWeight]) {
			   bestSolution.add(bags[i-1]);  // bags[i-1] 表示第 i 个背包
			   tempWeight -= bags[i-1].getWeight();
		   }
		   if (tempWeight == 0) { break; }
	    }
	    bestValue = bestValues[n][totalWeight];
   	}
	
	/**
	 * 获得前  n 个背包， 总承重为 totalWeight 的背包问题的最优解值
	 * 调用条件： 必须先调用 solve 方法
	 * 
	 */
	public int getBestValue() {	
		return bestValue;
	}
	
	/**
	 * 获得前  n 个背包， 总承重为 totalWeight 的背包问题的最优解值矩阵
	 * 调用条件： 必须先调用 solve 方法
	 * 
	 */
    public int[][] getBestValues() {
    	
    	return bestValues;
    }
    
    /**
	 * 获得前  n 个背包， 总承重为 totalWeight 的背包问题的最优解值矩阵
	 * 调用条件： 必须先调用 solve 方法
	 * 
	 */
    public ArrayList<Knapsack> getBestSolution() {
    	return bestSolution;
    }

	
}
