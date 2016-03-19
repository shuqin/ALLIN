package zzz.study.patterns.observer.realconfig.interact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 中介者模式，管理观察者与被观察者的交互
 * 实际应用中，可作为一个Spring Singleton Bean来管理
 */
public class ObserverMediator {

    private Map<Config, List<ConfigObserver>> mediator = new HashMap<>();

    /**
     * 应用启动时初始化
     */
    public void init() {
    }

    public synchronized boolean register(Config config, ConfigObserver configObserver) {
        List<ConfigObserver> oberverList = mediator.get(config);
        if (oberverList == null) {
            oberverList = new ArrayList<>();
        }
        oberverList.add(configObserver);
        mediator.put(config, oberverList);
        return true;
    }

    public synchronized boolean unregister(Config config, ConfigObserver configObserver) {
        List<ConfigObserver> oberverList = mediator.get(config);
        if (oberverList == null) {
            return false;
        }
        oberverList.remove(configObserver);
        mediator.put(config, oberverList);
        return true;
    }

    public synchronized boolean notifyAll(Config config) {
        List<ConfigObserver> configObservers = mediator.get(config);
        configObservers.forEach(
                observer -> observer.getUpdateFunc().apply(config)
        );
        return true;
    }
}
