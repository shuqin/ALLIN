package zzz.study.foundations.exception;

public class NestTry {
	
	public static void main(String[] args)
	{
		try {
			System.out.println("Do something... ");
			for (int i=0; i < 3; i++) { 
				try {
					System.out.println("i = " + i);
					if (i == 2)
					   throw new Exception("Nested exception.");
				} finally { // 在外层捕获内层异常时，这里总要被执行。
					System.out.println("nested finally always executed.");
				}
				System.out.println("here is not always executed.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
