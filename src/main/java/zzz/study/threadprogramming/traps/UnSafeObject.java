package zzz.study.threadprogramming.traps;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

@Setter
@Getter
public class UnSafeObject {

    private int i = 0;

    public static void main(String[] args) {
        UnSafeObject unSafeObject = new UnSafeObject();

        ThreadStarter.startMultiThreads(
                (ti) -> {
                    unSafeObject.setI(ti);
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                    }
                    System.out.println("Thread" + ti + ":" + unSafeObject.getI());
                }
        );
    }
}


