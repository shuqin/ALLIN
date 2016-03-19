package zzz.study.threadprogramming.threadsafe;

public class AtomicObject {
	
private int count;
	
	public AtomicObject()
	{
		this.count = 0;
	}
	
	public AtomicObject(int count)
	{
		this.count = count;
	}
	
	public synchronized int increAndGetCount()
	{
		return ++count;
	}
	
	public synchronized void increCount()
	{
		++count;
	}
	
	public int getCount() 
	{
		return count;
	}

}
