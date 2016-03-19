package zzz.study.threadprogramming.basic;

import zzz.study.threadprogramming.basic.dataconcurrency.PrimePrintSingleThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Joiner implements Runnable {

    private PrimePrintSingleThread ppst;

    public Joiner(PrimePrintSingleThread ppst) {
        this.ppst = ppst;
    }

    public static void main(String[] args) {
        PrimePrintSingleThread ppst = new PrimePrintSingleThread(10000);
        Joiner joiner = new Joiner(ppst);
        ExecutorService exec = Executors.newCachedThreadPool();
//		exec.execute(ppst);
        exec.execute(joiner);
        exec.shutdown();
    }

    public void run() {
//		for (int i=0; i<1000; i++) {
//			System.out.println( "[" + i + "]: " + Math.sqrt(i));
//			if (i == 500) {
//				try {
//					ppst.join();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}

    }

}
