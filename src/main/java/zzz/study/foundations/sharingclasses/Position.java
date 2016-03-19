package zzz.study.foundations.sharingclasses;

import java.util.HashSet;
import java.util.Set;

public class Position implements Comparable<Position> {
	private int x;
	private int y;
	public Position(int x, int y) {
		this.x = x; this.y = y;
	}
	
	public int distance() {
		return x*x + y*y;
	}
	
	public int compareTo(Position p) {
		
		int thisDistance = distance();
		int pDistance = p.distance();
		
		if (thisDistance > pDistance) {
			return 1;
		} else if (thisDistance < pDistance) {
			return -1;
		} else {
			return 0;
		}
		
	}
	
	public boolean equals(Object obj) {
		return obj instanceof Position &&
		       ((Position)obj).x == x && ((Position)obj).y == y;
	}
	
	public int hashCode() {
		return 17 + x + y;
	}
	
	public String toString()
	{
		return "[" + x + " , " + y + "]";
	}
	
	static class PositionTester {
		public static void main(String[] args) 
		{
			Position p = new Position(1, 2);
			Position anPosition = new Position(1, 2);
			System.out.println(p.equals(null));
			System.out.println(p.equals(p));
			System.out.println(p.equals(anPosition));
			System.out.println(anPosition.equals(p));
			Set<Position> pSet = new HashSet<Position>();
			pSet.add(p);
			pSet.add(anPosition);
			System.out.println(pSet);
		}
	}
}

