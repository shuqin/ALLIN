package zzz.study.javatech.serialization;

import java.io.Serializable;

public class Point2 implements Serializable {
	
	// 添加了 serialVersionUID
	
	private static final long serialVersionUID = 1L;
	
	private int x;
	 private int y;  // 在对象序列化后添加 字段 y ， 模拟软件版本升级
	
	public Point2()
	{
		this.x = 0;
	}
	
	public Point2(int x) {
		this.x = x;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public int getX()
	{
		return x;
	}
	
	public String toString()
	{
//	    return "" + x;
		 return "(" + x + ", " + y + ")"; // 添加字段 y 后
	}

}
