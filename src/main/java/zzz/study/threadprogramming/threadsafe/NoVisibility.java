package zzz.study.threadprogramming.threadsafe;

public class NoVisibility {
    private static boolean ready;
    private static int number;

    public static void main(String[] args) {
        new ReaderThread().start();
        Thread.yield();
        number = 42;
        ready = true;
        System.out.println("main thread executed.");
    }

    private static class ReaderThread extends Thread {
        public void run() {
            System.out.println(this.getClass().getSimpleName() + " executed.");
            while (!ready)
                Thread.yield();
            System.out.println(number);
        }
    }
}

