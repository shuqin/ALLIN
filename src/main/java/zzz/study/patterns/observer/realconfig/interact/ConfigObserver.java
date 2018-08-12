package zzz.study.patterns.observer.realconfig.interact;

import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfigObserver {

  private Application app;
  private Function<Config, Object> updateFunc;

  @Override
  public boolean equals(Object c) {
    if (c == null || ! (c instanceof ConfigObserver)) {
      return false;
    }
    ConfigObserver cmp = (ConfigObserver) c;
    return cmp.getApp().equals(this.getApp());
  }

  @Override
  public int hashCode() {
    return app.hashCode();
  }
}
