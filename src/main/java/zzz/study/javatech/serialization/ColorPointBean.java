package zzz.study.javatech.serialization;

import java.awt.Color;

public class ColorPointBean {
	
	private double x;
	private double y;
	private Color  color;
	
	private transient String note = "A ColorPoint Bean";
	
	public ColorPointBean() {}
	public ColorPointBean(double x, double y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public ColorPointBean(double x, double y) {
		this(x,y, Color.BLACK);
	}
	
	public String toString() {
		String colorDesc = describe(color);
		return "[" + x + ", " + y + "]: " + colorDesc;
	}
	
	private String describe(Color color) {
	    return "(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ")";
	    
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	

}
