package zzz.study.patterns.observer.realconfig;

public class AApplication extends AbstractApplication {

  @Override
  public void handle(Object conf) {
    System.out.println("A Conf updated: " + conf);
  }

}
