package zzz.study.patterns.observer.realconfig.interact;

public class BApp extends Application {

  public BApp() {
    super.setId();
  }

  public Object hba(Config c) {
    System.out.println("hba: " + c.getConf());
    return c;
  }

  public Object hbb(Config c) {
    System.out.println("hbb: " + c.getConf());
    return c;
  }

  public Object hbc(Config c) {
    System.out.println("hbc: " + c.getConf());
    return c;
  }

}
