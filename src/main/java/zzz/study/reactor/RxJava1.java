package zzz.study.reactor;

import com.alibaba.fastjson.JSON;
import rx.Observable;
import rx.Subscriber;

/**
 * @Description TODO
 * @Date 2021/2/8 9:54 下午
 * @Created by qinshu
 */
public class RxJava1 {

    public static void main(String[]args) {

        (Observable.just(1)).single().subscribe(new Subscriber() {

            @Override
            public void onCompleted() {
                System.out.println("Subscriber onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Subscriber error");
            }

            @Override
            public void onNext(Object o) {
                System.out.println("Subscriber onNext:" + JSON.toJSONString(o));
            }
        });
    }
}
