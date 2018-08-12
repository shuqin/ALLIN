package zzz.study.patterns.observer.realconfig.interact;

public abstract class Application extends ID {

  @Override
  public boolean equals(Object c) {
    return equals(c, Application.class);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

}
