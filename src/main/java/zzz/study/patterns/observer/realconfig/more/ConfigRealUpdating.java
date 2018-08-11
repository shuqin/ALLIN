package zzz.study.patterns.observer.realconfig.more;


import zzz.study.patterns.observer.realconfig.simple.Config;

public class ConfigRealUpdating {

  public static void main(String[] args) {

    Config aConfig = new AConfig("Haha");
    Config bConfig = new BConfig(-1L);
    AbstractApplication aApp = new AApplication();
    AbstractApplication bApp = new BApplication();
    aConfig.addObserver(aApp);
    bConfig.addObserver(bApp);

    aConfig.update("I am changed");
    aConfig.notifyObservers();
    bConfig.update(9L);
    bConfig.notifyObservers();
  }

}
