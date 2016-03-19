package zzz.study.patterns.builder;


public class Reservation {
	
	private String date;
	private int  headcount;
	private String city;
	private double yuanPerHead;
	private boolean hasSite;
	
	public Reservation(String date, int headcount, String city,
			double yuanPerHead, boolean hasSite) {

		this.date = date;
		this.headcount = headcount;
		this.city = city;
		this.yuanPerHead = yuanPerHead;
		this.hasSite = hasSite;
		
	}
	
	public String toString() {
		
		String s = "";
		s += "Date: " + date + "\n";
		s += "headcount: " + headcount + "\n";
		s += "city: " + city + "\n";
		s += "yuan/Head: " + yuanPerHead + "\n";
		s += "HasSite: " + hasSite + "\n";
		return s;
		
	}
	

}
