package zzz.study.threadprogramming.threadsafe;

public class NoAtomicObject {
	
	private int count;
	
	public NoAtomicObject()
	{
		this.count = 0;
	}
	
	public NoAtomicObject(int count)
	{
		this.count = count;
	}
	
	public int increAndGetCount()
	{
		return ++count;
	}
	
	public void increCount()
	{
		++count;
	}
	
	public int getCount() 
	{
		return count;
	}
	
	

}
