package zzz.study.foundations.array;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;

public class ArrayOfExceptions {
	
	public static final int DEFAULT_SIZE = 50;
	private Exception[] exceptions; 
	
	public ArrayOfExceptions() {
		if (exceptions == null) {
			exceptions = new Exception[DEFAULT_SIZE];
		}
	}
	
	public ArrayOfExceptions(int size) {
		if (exceptions == null) {
			exceptions = new Exception[size];
		}
	}
	
	public ArrayOfExceptions(Exception[] exceptions) {
		this.exceptions = exceptions;
	}

	public void showThisArray() {
		System.out.println(Arrays.toString(exceptions));
	}
	
	public Exception getException(int i) {
		if (i < 0 || i >= exceptions.length)
			throw new IllegalArgumentException("Parameter Out Of Bound. " + "i = " + i);
		return exceptions[i];
	}
	
	public void setExceptions(int i, Exception e) {
		if (i < 0 || i >= exceptions.length) {
			throw new IllegalArgumentException("Parameter Out Of Bound. " + "i = " + i);
		}
		exceptions[i] = e;
	}
	
	public static void main(String[] args) {
		
		ArrayOfExceptions ae = new ArrayOfExceptions(10);
		System.out.println("ae: ");
		ae.showThisArray();
		
		ae = new ArrayOfExceptions(new Exception[] {
			new IllegalArgumentException(),
			new RuntimeException(), null, null,
			new ArithmeticException(),
			new SQLException(), null, null, null, new NoSuchMethodException()});
		System.out.println("ae: ");
		ae.showThisArray();
		
		try {			
			ae.setExceptions(1, new Exception());
		    ae.setExceptions(3, new NullPointerException());
		    ae.setExceptions(5, new RuntimeException());
		    ae.setExceptions(7, new IllegalArgumentException());
		    	    
		    Exception e = ae.getException(3);
			System.out.println("所取得的元素：" + e);
		    Exception e2 = ae.getException(-1);
			System.out.println("所取得的元素：" + e2);
		    
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("ae: ");
			ae.showThisArray();
		}
		
		System.out.println("------------------------------------------------");
		
		Exception[] exceptions = new Exception[] {
			new Exception(), new RuntimeException(),
			new NullPointerException(), new IllegalArgumentException(), 
			new SQLException(), new ArithmeticException()
		};
		
		try {
			Arrays.sort(exceptions, new CompareByClassName());
		} catch (ClassCastException e) {
			System.out.printf("数组中含有不可比较的元素 ----");
			System.out.println(" 数组排序时所有元素在所采用的比较器上是必须可以相比计较的！");
			e.printStackTrace(System.out);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		finally {
			System.out.println("根据异常类名排序后的结果：");
			System.out.println(Arrays.toString(exceptions));
		}
		
		
		// 比较 Arrays.toString 和 Arrays.deepToString 
		int[][] twoDimenArray = new int[][] {
				new int[]{1,2,3}, new int[]{4,5,6}, new int[]{7,8,9}
		};
		System.out.println(" ----- Arrays.toString ----- ");
		System.out.println(Arrays.toString(twoDimenArray));
		System.out.println(" ----- Arrays.deepToString ----- ");
		System.out.println(Arrays.deepToString(twoDimenArray));
	}	

}

class CompareByClassName implements Comparator {
	
	public int compare(Object e1, Object e2) {
		String e1OfName = e1.getClass().getSimpleName();
		String e2OfName = e2.getClass().getSimpleName();
		return e1OfName.compareTo(e2OfName);
	}
}
