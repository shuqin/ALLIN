package zzz.study.threadprogramming.traps;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UncontrolledLocalThreadPool {

    public static void main(String[] args) {
        int n = 100000;
        for (int i = 0; i < n; i++) {
            freqCalledMethod();
        }
        System.out.println("here");
    }

    public static void freqCalledMethod() {
        ExecutorService threadExecutor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            threadExecutor.submit(() -> 9999L * 9999L);
        }
    }
}
