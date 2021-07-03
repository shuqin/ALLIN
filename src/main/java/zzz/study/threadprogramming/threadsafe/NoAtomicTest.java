package zzz.study.threadprogramming.threadsafe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoAtomicTest {

    public static void main(String[] args) {
        final NoAtomicObject nao = new NoAtomicObject();
        System.out.println("Init count: " + nao.getCount());

        ExecutorService exec = Executors.newCachedThreadPool();
        Runnable[] runs = new Runnable[5];
        for (int i = 0; i < 5; i++) {
            runs[i] = new Runnable() {
                public void run() {
                    System.out.println(this + "  count: " + nao.increAndGetCount());
                }
            };
            exec.execute(runs[i]);
        }

        System.out.println(" --- lazy initialization --- ");

        UnSafeLazyInitialization uli = new UnSafeLazyInitialization();
        ExpensiveObject ex1 = uli.getInstance();
        ExpensiveObject ex2 = uli.getInstance();
        System.out.println("ex1 == ex2 ? " + ((ex1 == ex2) ? "Yes" : "No"));

    }

}

/*
 * Note:
 * nao.increCount() 中  count++ 看上去像是原子操作，实际上是复合操作：
 * 将 count 取出， 加一，然后存储。
 *
 * 当多个线程访问到 count 时， 假设 线程 【A】已经将 count 由 1 修改为 2，获取到值  2 ；
 * 而线程【B】没有看到这一改变， 仍然观察到 count = 1;
 * 那么，线程【B】 就会将 count 由 1 修改为 2， 获取到值 2 ；
 *
 * 当 count 作为 对象的唯一性标识时， 就会产生严重的错误，破坏数据的完整性。
 *
 * a unexpected result caught:
 * Init count: 0
 *
 * basic.NoAtomicTest$1@1389e4  count: 1
 * basic.NoAtomicTest$1@c20e24  count: 1
 * basic.NoAtomicTest$1@e89b94  count: 2
 * basic.NoAtomicTest$1@13e205f  count: 3
 * basic.NoAtomicTest$1@1bf73fa  count: 4
 *
 * 当线程 @1389e4 将 count 值 设置为 1 时，
 * 线程 @c20e24 并没有看到这一改变， 而是也将 count 由 0 置为 1.
 *
 */
