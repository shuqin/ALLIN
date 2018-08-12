package zzz.study.patterns.observer.realconfig.interact;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 封装了ID以及根据ID来比较的功能
 */
public abstract class ID {

  private static AtomicLong gloalId = new AtomicLong(0);

  // 通过id字段标识配置及应用
  protected Long id;

  public void setId() {
    this.id = gloalId.addAndGet(1);
  }

  public <T> boolean equals(Object obj, Class<T> cls) {
    if (obj == null || ! (cls.isInstance(obj))) {
      return false;
    }
    return ((ID)obj).id.equals(this.id);
  }

  public int hashCode() {
    return id.hashCode();
  }


}
