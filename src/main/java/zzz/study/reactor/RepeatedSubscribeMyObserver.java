package zzz.study.reactor;

import com.alibaba.fastjson.JSON;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

/**
 * @Description 可重复订阅的观察者
 * @Date 2021/1/24 10:11 上午
 * @Created by qinshu
 */
public class RepeatedSubscribeMyObserver implements Observer {

    public Disposable upstream;

    @Override
    public void onSubscribe(@NonNull Disposable d){
        System.out.println("RepeatedSubscribeMyObserver: Start");
        this.upstream = d;
    }

    @Override
    public void onNext(Object o) {
        System.out.println("RepeatedSubscribeMyObserver: " + JSON.toJSONString(o));
    }

    @Override
    public void onError(Throwable e) {
        System.out.println("RepeatedSubscribeMyObserver: " + e.getMessage());
        cancel();
    }

    @Override
    public void onComplete() {
        System.out.println("RepeatedSubscribeMyObserver: Complete");
        cancel();
    }

    /**
     * Cancels the upstream's disposable.
     */
    protected final void cancel() {
        Disposable upstream = this.upstream;
        this.upstream = DisposableHelper.DISPOSED;
        upstream.dispose();
    }
}
