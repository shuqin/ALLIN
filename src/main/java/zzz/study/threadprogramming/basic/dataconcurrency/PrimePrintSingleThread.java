package zzz.study.threadprogramming.basic.dataconcurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrimePrintSingleThread {

    // primes: 用来存储所获得的质数。 
    private List<Long> primes;

    private long range;

    public PrimePrintSingleThread(long range) {
        if (primes == null) {
            primes = new ArrayList<Long>();
        }
        this.range = range;
    }

    public static void main(String[] args) {
        long range = 1000000;
        PrimePrintSingleThread ppst = new PrimePrintSingleThread(range);
        long start = System.currentTimeMillis();
        ppst.runAndPrint();
        long end = System.currentTimeMillis();
        System.out.println("Eclipsed:" + (end - start) + " ms.");

        System.out.println("**** 直接获取最近一次运行的结果 ****");
        ppst.printPrimes();

        System.out.println("**** 作新设置后运行的结果 ****");

        ppst.setRange(1000);
        ppst.runAndPrint();
    }

    public void setRange(long range) {
        this.range = range;
    }

    // 筛选出 1 - rangle 之间的所有质数【不包括 range】
    private void filterPrimes() {
        for (long i = 1L; i < range; i++) {
            if (Prime.isPrime(i))
                primes.add(i);
        }
    }

    // 清除前一次的线程运行结果
    private void clearLastResult() {
        if (primes != null && primes.size() > 0) {
            primes.clear();
        }
    }

    /**
     * 若进行了新的设置，并想获取或打印新的最终质数列表结果，必须先运行此方法【亦可直接运行方法 runAndPrint】。
     * <p>
     * 注意： 此方法运行一次后的结果会进行保存，并可调用方法  getPrimesList 或 printPrimes 获取或打印；
     * <p>
     * 如果只想获取或打印上一次运行的结果，就不必运行此方法。
     */
    public void run() {
        clearLastResult();
        filterPrimes();
    }

    /**
     * 此方法专门用于获取最近一次运行所得到的最终质数列表
     */
    public List getPrimesList() {
        return Collections.unmodifiableList(primes);
    }

    /**
     * 此方法专门用于打印最近一次运行所得到的最终质数列表
     */
    public void printPrimes() {
        System.out.println("****** Print all the primes in range < " + range + " ******");
        int count = 0;
        for (long i : primes) {
            System.out.printf(i + " ");
            // print ten primes per line 
            count++;
            if (count % 10 == 0)
                System.out.println();
        }
        System.out.println("\n总共质数数目： " + primes.size());
    }

    /**
     * 此方法用于在一次运行得到并打印最终质数列表
     */
    public void runAndPrint() {
        run();
        printPrimes();
    }

}
