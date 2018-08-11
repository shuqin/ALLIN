package zzz.study.patterns.observer.realconfig;


public class ConfigRealUpdating {

  public static void main(String[] args) {
    Config config = new Config<>(5);
    Application app = new Application();
    config.addObserver(app);

    config.update(6);
    config.notifyObservers();

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
