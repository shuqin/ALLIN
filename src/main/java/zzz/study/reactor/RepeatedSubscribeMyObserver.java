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
public class RepeatedSubscribeMyObserver<T> implements Observer<T> {

    public Disposable upstream;

    @Override
    public void onSubscribe(@NonNull Disposable d){
        System.out.println(getName() + ": Start");
        this.upstream = d;
    }

    @Override
    public void onNext(T o) {
        System.out.println(getName() + ": " + JSON.toJSONString(o));
    }

    @Override
    public void onError(Throwable e) {
        System.out.println(getName() + "RepeatedSubscribeMyObserver: " + e.getMessage());
        cancel();
    }

    @Override
    public void onComplete() {
        System.out.println(getName() + "RepeatedSubscribeMyObserver: Complete");
        cancel();
    }

    public String getName() {
        return this.getClass().getSimpleName();
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
