package zzz.study.reactor;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.internal.functions.Functions;

import java.util.concurrent.FutureTask;

/**
 * @Description TODO
 * @Date 2021/1/23 4:13 下午
 * @Created by qinshu
 */
public class MyDisposable {

    public static Disposable build() {
        Disposable d1 = Disposables.fromRunnable(() -> {
            System.out.println("Runnable disposable.");
        });

        Disposable d2 = Disposables.fromAction(
                Functions.futureAction(new FutureTask<>(() -> {
                    System.out.println("ActionDisposable FutureTask");
                    return 10000;
                }))
        );

        return new CompositeDisposable(d1, d2);
    }
}
