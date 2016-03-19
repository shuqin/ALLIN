package zzz.study.algorithm.problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SqrtTest {
	
	public static void main(String[] args) {
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));	
		try {
			String line = ""; 
			while ((line = stdin.readLine()) != null) {
				System.out.println("read : " + line);
				if (line.matches("^\\s*(0|[1-9][0-9]*)\\s+(0?.[0-9]+)$")) {
					String[] nums = line.trim().split("\\s+");
						int n = Integer.parseInt(nums[0]);
						double error = Double.parseDouble(nums[1]);
						System.out.println("上下限试探法： " + n + " 的平方根为：" + Sqrt.mysqrt(n, error) + "\t 误差为 " + error);
						System.out.println("使用库函数： "  + n + " 的平方根为：" + Math.sqrt(n));
				}
			    else 
			    {
			    	throw new IllegalArgumentException("输入有误! 输入格式：[零个或多个空白符][一个正整数][一个或多个空白符][一个正浮点数]");
			    }	
			}	
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
