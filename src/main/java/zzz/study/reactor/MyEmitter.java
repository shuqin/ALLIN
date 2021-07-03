package zzz.study.reactor;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Description 发射装置
 * @Date 2021/1/24 7:04 上午
 * @Created by qinshu
 */
public class MyEmitter implements ObservableOnSubscribe {

    Random random = new Random(System.currentTimeMillis());

    @Override
    public void subscribe(ObservableEmitter emitter) throws Exception {
        TimeUnit.SECONDS.sleep(1);
        emitter.onNext("next");
        if (random.nextInt(3) == 0) {
            emitter.onError(new RuntimeException("A RuntimeException"));
        } else {
            emitter.onComplete();
        }

    }
}
