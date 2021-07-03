package zzz.study.threadprogramming.basic.simulation;

import java.util.Random;

public class Producer extends Thread {

    private static String str = "abc1defg2hijk3lmno4pqrs5tuvwx6yz" +
            "AB7CDEF8GHIJK9LMNO0PQR_STU*VWXYZ";

    private static volatile boolean endflag = false;

    private final int id;

    CharBuffer buffer;

    public Producer(int id, CharBuffer buffer) {
        this.id = id;
        this.buffer = buffer;
    }

    public static void cancel() {
        endflag = true;
    }

    public boolean isCanceled() {
        return endflag == true;
    }

    public void run() {
        while (!isCanceled()) {
            synchronized (buffer) {
                while (buffer.isFull()) {
                    // 缓冲区已满，生产者必须等待
                    try {
                        buffer.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted.");
                    }
                }
                char ch = produce();
                System.out.println(TimeIndicator.getcurrTime() + ":\t" + this + " 准备写缓冲区：" + ch);
                buffer.write(ch);
                System.out.println(TimeIndicator.getcurrTime() + ":\t" + this + " :\t\t\t" + buffer);
                buffer.notify();
            }
            Thread.yield();
        }
    }

    public char produce() {
        Random rand = new Random();
        return str.charAt(rand.nextInt(64));
    }

    public String toString() {
        return "P[" + id + "]";
    }

}
