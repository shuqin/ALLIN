package zzz.study.algorithm.select;

import static zzz.study.algorithm.select.RandomSelector.*;
import zzz.study.algorithm.runtime.Command;
import zzz.study.algorithm.runtime.CommandRuntime;
import zzz.study.utils.TableUtil;


public class RandomSelectorRuntime {
	
	public static void main(String[] args) 
	{
		System.out.println("*********** �� n �����������ѡ�� m ������������б? �ٶȱȽ� *************");
		int[] row = new int[] { 10, 100, 1000, 10000, 100000};
		int[] col = new int[] { 100001, 1000001, 10000001, 100000001};
		int[][] prsResult = new int[row.length][col.length];
		int[][] dcsResult = new int[row.length][col.length];
		int[][] ersResult = new int[row.length][col.length];
		ProbablityRandomSelector prs = new ProbablityRandomSelector(1,1);
		DupCheckRandomSelector dcs = new DupCheckRandomSelector(1, 1);
		ExchangeRandomSelector ers = new ExchangeRandomSelector(1, 1); 
		for (int j=0; j < row.length; j++) {  // m
		    for (int i=0; i < col.length; i++) { // n			
				prs.set(row[j], col[i]); dcs.set(row[j], col[i]); ers.set(row[j], col[i]);
				prsResult[j][i] = (int) new CommandRuntime(prs).runtime();
				dcsResult[j][i] = (int) new CommandRuntime(dcs).runtime();
				ersResult[j][i] = (int) new CommandRuntime(ers).runtime();
			}
		}
		System.out.println("����ѡ������ʱ�䣺 (ms)\n ------------------------------- ");
		TableUtil.printTable(row, col, prsResult);
		System.out.println();
		System.out.println("�ظ����ѡ������ʱ�䣺(ms)\n ----------------------------- ");
		TableUtil.printTable(row, col, dcsResult);
		System.out.println();
		System.out.println("���ѡ������ʱ�䣺 (ms)\n ----------------------------- ");
		TableUtil.printTable(row, col, ersResult);
		
	}
	

}

class ProbablityRandomSelector implements Command {

	private int m;
	private int n;
	
	public ProbablityRandomSelector(int m, int n) {
		this.m = m; this.n = n;
	}
	
	public void set(int m, int n) {
		this.m = m; this.n = n;
	}
	
	public void run() {
		selectMOrderedRandInts(m, n);
	}
	
}

class DupCheckRandomSelector implements Command {
	
	private int m;
	private int n;
	
	public DupCheckRandomSelector(int m, int n) {
		this.m = m; this.n = n;
	}
	
	public void set(int m, int n) {
		this.m = m; this.n = n;
	}
	
	public void run() {
		selectMOrderedRandInts2(m, n);
	}
	
}

class ExchangeRandomSelector implements Command {
	
	private int m;
	private int n;
	
	public ExchangeRandomSelector(int m, int n) {
		this.m = m; this.n = n;
	}
	
	public void set(int m, int n) {
		this.m = m; this.n = n;
	}
	
	public void run() {
		selectMOrderedRandInts3(m, n);
	}
	
}
