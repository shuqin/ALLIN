package zzz.study.patterns.observer.realconfig.interact;

import lombok.Getter;

@Getter
public class Config<T> extends ID {

  private T conf;
  private ObserverMediator mediator;

  public Config(T conf, ObserverMediator mediator) {
    super.setId();
    this.conf = conf;
    this.mediator = mediator;
  }

  public void update(T config) {
    this.conf = config;
    mediator.notifyAll(this);
  }

  @Override
  public boolean equals(Object c) {
    return equals(c, Config.class);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

}
