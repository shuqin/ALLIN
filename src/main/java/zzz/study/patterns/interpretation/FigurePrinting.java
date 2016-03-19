package zzz.study.patterns.interpretation;

public class FigurePrinting {
	
	/**
	 * 根据给定 n 值打印菱形图案，n 为菱形边长
	 */
	public void printDiamond(int n)
	{
		if (n < 2) {
			throw new IllegalArgumentException("参数 n = " + n + " 错误， 给定参数必须不小于 2！");
		}
		int midRow = n-1; 
		for (int row = 0; row <= midRow; row++) {
			printLine(new int[] { midRow-row , midRow+row}, 2*n-1, '*');
		}
		for (int row = midRow+1; row < 2*n-1; row++) { // 对称性： row = midRow+i 与 row = midRow-i 的图案是一样的
			printLine(new int[] { row - midRow, midRow + (2*midRow - row)}, 2*n-1, '*');
		}
	}

	/**
	 * printLine: 在 indexes 下标数组【从零数起】所指定的若干位置上打印符号 symbol ,
	 *            其它位置打印空格, 总共打印的字符数为 totalCharNum
	 * NOTE: eg. printLine(new int[] {1,4,5}, 6, 'c') 将打印出  c  cc
	 */
	public void printLine(int[] indexes, int totalCharNum, char symbol)
	{
		if (indexes == null || indexes.length == 0) { // 如果位置数组为空，则直接换行
			System.out.println();
			return ;
		}
		if (totalCharNum < indexes.length) {
			throw new IllegalArgumentException("要打印的符号数超过一行的总字符数!");
		}
		char[] charsToBePrinted = new char[totalCharNum];
		for (int i=0; i < totalCharNum; i++) {
			charsToBePrinted[i] = ' ';
		}
		for (int i=0; i < indexes.length; i++) {
			if (indexes[i] < totalCharNum) {     // 丢弃超出 totalCharNum 的无效下标
				charsToBePrinted[indexes[i]] = symbol;
			}	
		}
		for (int i=0; i < totalCharNum; i++) {
			System.out.printf("%c", charsToBePrinted[i]);
		}
		System.out.println();
	}	 
}
