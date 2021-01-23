package zzz.study.reactor;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;

/**
 * @Description RxJava基本Demo
 * @Date 2021/1/23 12:28 下午
 * @Created by qinshu
 */
public class RxJavaBasic {

    public static void main(String[] args) {

        for (int i=0; i<5; i++) {
            ObservableOnSubscribe observableOnSubscribe = new MyEmitter();
            Observable observable = Observable.create(observableOnSubscribe);

            Observer observer = new MyObserver();
            observable.subscribe(observer);
        }
    }
}


