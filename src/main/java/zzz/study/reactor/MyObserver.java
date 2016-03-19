package zzz.study.reactor;

import com.alibaba.fastjson.JSON;
import io.reactivex.observers.DefaultObserver;

/**
 * @Description 观察者定义
 * @Date 2021/1/23 4:13 下午
 * @Created by qinshu
 */
public class MyObserver extends DefaultObserver {

    @Override
    public void onStart() {
        System.out.println("MyObserver: Start");
    }

    @Override
    public void onNext(Object o) {
        System.out.println("Observed: " + JSON.toJSONString(o));
    }

    @Override
    public void onError(Throwable e) {
        System.out.println("Observed: " + e.getMessage());
        super.cancel();
    }

    @Override
    public void onComplete() {
        System.out.println("MyObserver: Complete");
        super.cancel();
    }
}
