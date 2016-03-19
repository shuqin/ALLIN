package zzz.study.threadprogramming.basic;

import java.io.IOException;
import java.util.concurrent.Executors;

public class ResponsiveUI extends Thread {

    private static int d = 1;

    public ResponsiveUI() {
        setDaemon(true);
    }

    public static void main(String[] args) {
        ResponsiveUI rui = new ResponsiveUI();
        Executors.newCachedThreadPool().execute(rui);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (d > 0) {
            System.out.println("d = " + d);
            d++;
        }
    }

}
