package zzz.study.patterns.observer.realconfig.simple;

import java.util.Observable;

public class Config<T> extends Observable {

    private T conf;

    public Config(T conf) {
        this.conf = conf;
    }

    public T getConf() {
        return conf;
    }

    public void update(T config) {
        this.conf = config;
        setChanged();
    }

}
