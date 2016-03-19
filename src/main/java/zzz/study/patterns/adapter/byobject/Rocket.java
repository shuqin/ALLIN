package zzz.study.patterns.adapter.byobject;

public class Rocket {

	private String name;
	private double price;
	private double apogee; // 火箭远地点	
	
	public Rocket(String name, double price, double apogee) {
		this.name = name;
		this.price = price;
		this.apogee = apogee;
	}
	
	public Rocket copy(Rocket r)
	{
		return new Rocket(r.getName(),r.getPrice(), r.getApogee());
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getApogee() {
		return apogee;
	}
	public void setApogee(double apogee) {
		this.apogee = apogee;
	}
	
	public String toString() {
		return "(" + name + "," + price + "," + apogee + ")";
	}
	
}
