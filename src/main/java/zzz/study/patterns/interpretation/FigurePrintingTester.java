package zzz.study.patterns.interpretation;

public class FigurePrintingTester {
	
	private static FigurePrinting fp = new FigurePrinting();
	
	public static void testPrintLine()
	{
		fp.printLine(null, 3, '*');
		fp.printLine(new int[0], 3, '*');
		fp.printLine(new int[] {}, 3, '*');
		fp.printLine(new int[3] , 3, '*');
		fp.printLine(new int[] {2,3}, 3, '*');
		fp.printLine(new int[] { 0, 1, 2 }, 3, '*');
	}
	
	public static void testPrintDiamond()
	{
		for (int k = 1; k < 5; k++) {
			try {
				System.out.printf("边长为 %d 的菱形: \n", k);
				fp.printDiamond(k);
			} 
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static void main(String[] args)
	{
		testPrintLine();
		testPrintDiamond();
	}

}
