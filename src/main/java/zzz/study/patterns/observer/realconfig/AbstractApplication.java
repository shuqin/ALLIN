package zzz.study.patterns.observer.realconfig;

import java.util.Observable;
import java.util.Observer;

public abstract class AbstractApplication implements Observer {

  public void update(Observable o, Object arg) {
    Config obj = (Config)o;
    Object conf = obj.getConf();
    handle(conf);
  }

  public abstract void handle(Object conf);

}
