package zzz.study.patterns.observer.realconfig.simple;

import java.util.Observable;
import java.util.Observer;

public class Application implements Observer {

    public void update(Observable o, Object arg) {
        Config obj = (Config) o;
        Object conf = obj.getConf();
        System.out.println("conf updated: " + conf);
    }
}
