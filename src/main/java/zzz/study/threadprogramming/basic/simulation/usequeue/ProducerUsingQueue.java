package zzz.study.threadprogramming.basic.simulation.usequeue;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import zzz.study.threadprogramming.basic.simulation.TimeIndicator;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class ProducerUsingQueue extends Thread {
	
	private static String str = "abc1defg2hijk3lmno4pqrs5tuvwx6yz" +
    "AB7CDEF8GHIJK9LMNO0PQR_STU*VWXYZ";
	
	private static volatile boolean endflag = false;
	
	private final int id;
	
	BlockingQueue<Character> buffer;

	private Logger log = Logger.getLogger("appInfo");
	
	public ProducerUsingQueue(int id, BlockingQueue<Character> buffer) {
		this.id = id;
		this.buffer = buffer;
	}
	
	public static void cancel() {
		endflag = true;
	}
	
	public boolean isCanceled() {
		return endflag == true;
	}
	
	public void run()
	{
		while (!isCanceled()) {
			try {
				char ch = produce();
				log.info(TimeIndicator.getcurrTime() + ":\t" + this + " 准备写缓冲区：" + ch);
				buffer.put(ch);
				log.info(TimeIndicator.getcurrTime() + ":\t" + this + " :\t\t\t" + buffer);
			} catch (InterruptedException e) {
				log.error(this + " Interrupted: " + e.getMessage());
			}
		}
	}
	
	public char produce()
	{
		Random rand = new Random(); 
		return str.charAt(rand.nextInt(64));
	}
	
    public String toString()
    {
    	return "P[" + id + "]";
    }

}
