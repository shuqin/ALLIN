/**
 * CharOutputThread:
 * 通过创建线程，并使用CharBuffer来实现并发地读和写字符缓冲区的仿真
 */

package zzz.study.threadprogramming.basic.simulation;

public class Consumer extends Thread {

    private static volatile boolean endflag = false;

    private final int id;

    private CharBuffer buffer;

    public Consumer(int id, CharBuffer buffer) {
        this.id = id;
        this.buffer = buffer;
    }

    public static void cancel() {
        endflag = true;
    }

    public boolean isCanceled() {
        return endflag == true;
    }

    /**
     * consume:	
     * 当缓冲区buffer中有字符时，就取出字符显示【相当于消费者】。
     *
     */
    public char consume() {
        return buffer.fetch();
    }

    /**
     * run:
     * 一个被创建的任务，只要缓冲区不被置空，就从缓冲区中取出字符消费。
     */
    public void run() {

        while (!isCanceled()) {
            synchronized (buffer) {
                while (buffer.isEmpty()) {
                    // 缓冲区为空，消费者必须等待 ；
                    try {
                        buffer.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted.");
                    }
                }
                System.out.println(TimeIndicator.getcurrTime() + ":\t" + this + " 取出字符: " + consume());
                System.out.println(TimeIndicator.getcurrTime() + ":\t" + this + " :\t\t\t" + buffer);
                buffer.notify();
            }
            Thread.yield();
        }

    }

    public String toString() {
        return "C[" + id + "]";
    }


}
