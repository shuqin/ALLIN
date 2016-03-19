package zzz.study.datastructure.queue;

public class SeqQueue<T> implements Queue<T> {
	
	private final int MAX_QUEUE_SIZE = 10;
	
	private Object[] queue;
	private int front;
	private int rear;
	private int size;
	
	public SeqQueue()
	{
		queue = new Object[MAX_QUEUE_SIZE];
		front = 0;
		rear = 0;
		size = 0;
	}
	
	public boolean isEmpty()
	{
		return front == rear;
	}
	
	public int size()
	{
		return size;
	}
	
	public void clear()
	{
        for (int i=0; i < queue.length; i++) {
        	queue[i] = null;
        }
		front = 0;
		rear = 0;
		size = 0;	
	}
	
	public void enqueue(T e) throws Exception
	{
		int nextIndex = (rear + 1) % MAX_QUEUE_SIZE;
	    if (nextIndex == front) {
	    	throw new Exception("队列满，无法将元素入队！");
	    }
	    rear = nextIndex;
	    queue[rear] = e;
	    size++;
	}
	
	public T dequeue() throws Exception
	{
		if (isEmpty()) {
			throw new Exception("队列空，无法将元素出队！");
		}
		front = (front + 1) % MAX_QUEUE_SIZE;
		size--;
		return  (T) queue[front];
	}
	
	public String toString()
	{
		if (isEmpty()) {
			return "队列为空！";
		}
		int index = (front + 1) % MAX_QUEUE_SIZE;
		StringBuilder sb = new StringBuilder(" [ ");
		if (rear > front) {
			for (; index <= rear; index++) {
				sb.append(queue[index]);
				sb.append(' ');
			}
		}
		else if (rear < front){
			for (; index < MAX_QUEUE_SIZE; index++) {
				sb.append(queue[index]);
				sb.append(' ');
			}
			for (index = 0; index <= rear; index++) {
				sb.append(queue[index]);
				sb.append(' ');
			}
		}
		sb.append(']');
		return sb.toString();
	}

	
	public T head() {
		return (T) queue[(front + 1) % MAX_QUEUE_SIZE];
	}

	public T tail() 
	{
		return (T) queue[rear];
	}
	

}
