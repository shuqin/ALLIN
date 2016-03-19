package zzz.study.utils;

public class TableUtil {
	
	private TableUtil() {}
	
	/**
	 * printTable: 打印二维表格
	 * @param row 行标题内容
	 * @param col 列标题内容
	 * @param contents 表格内容
	 */
	public static void printTable(int[] row, int[] col, int[][] contents)
	{
		System.out.printf("%10s" , " ");	
		print("%10d", col);
		for (int i=0; i < row.length; i++) {
		    System.out.printf("%10d", row[i]);
		    print("%10d", contents[i]);
		}
	}
	
	/**
	 * nString: 将指定字符串连接自身指定次数后的新字符串返回
	 * @param s 指定字符串
	 * @param n 指定次数
	 * @return 指定字符串连接自身指定次数后的新字符串
	 */
	public static String nString(String s, int n) {
		StringBuilder sb = new StringBuilder();
		while (n > 0) {
			sb.append(s); 
			n--;
		}
		return sb.toString();
	}
	
	public static <T> void print(String format, T[] contents)
	{
		for (int i=0; i < contents.length; i++) {
			System.out.printf(format, contents[i]);
		}
		System.out.println();
	}
	
	public static void print(String format, int[] contents)
	{
		for (int i=0; i < contents.length; i++) {
			System.out.printf(format, contents[i]);
		}
		System.out.println();
	}
	
	static class Tester {
		
		public static void main(String[] args)
		{	
			print("%5d", new Integer[] {5,10,100,10,5});
			print("%3s", new String[] { "I", "have", "an", "dream."});
			
			System.out.println("*********************************");
			
			int[] row = new int[] { 10, 100, 1000, 10000 };
			int[] col = new int[] { 10000, 100000, 1000000};
			int[][] contents = new int[][] {
					{1,2,3}, {4,5,6}, {7,8,9}, {10,11,12}	
			};
			printTable(row, col, contents);
		}
	}

}
