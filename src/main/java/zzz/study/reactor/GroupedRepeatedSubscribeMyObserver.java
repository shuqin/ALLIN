package zzz.study.reactor;

import com.alibaba.fastjson.JSON;
import io.reactivex.observables.GroupedObservable;

/**
 * @Description 可重复订阅的分组观察者
 * @Date 2021/1/24 10:11 上午
 * @Created by qinshu
 */
public class GroupedRepeatedSubscribeMyObserver extends RepeatedSubscribeMyObserver<GroupedObservable> {

    @Override
    public void onNext(GroupedObservable o) {
        o.subscribe(new RepeatedSubscribeMyObserver() {

            @Override
            public void onNext(Object v) {
                String info = String.format("GroupedRepeatedSubscribeMyObserver: [group=%s][value=%s]", o.getKey(), JSON.toJSONString(v));
                System.out.println(info);
            }
        });

    }

}
