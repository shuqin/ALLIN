package zzz.study.patterns.observer.realconfig;


public class ConfigRealUpdating {

  public static void main(String[] args) {
    Config config = new Config<>(5);
    Application app = new Application();
    config.addObserver(app);

    config.update(6);
    config.notifyObservers();

  }

}
