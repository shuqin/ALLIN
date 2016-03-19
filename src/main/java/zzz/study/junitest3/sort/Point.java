package zzz.study.junitest3.sort;

public class Point implements Comparable {

	private double x;
	private double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Point() {
		this.x = 0;
		this.y = 0;
	}

	private double distance() {
		return x * x + y * y;
	}
	
	public int compareTo(Object obj) {
		Point point = (Point)obj;
		if (this.distance() == point.distance())
		    return 0;
		else if (this.distance() < point.distance())
			return -1;
		else
			return 1;
	}
	
	public boolean equals(Object obj) {
		Point point = (Point)obj;
		return x == point.x && y == point.y;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}
