package shared.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

public class BeanUtil {

  public static <T> T map2Bean(Map map, Class<T> c) {
    try {
      T t = c.newInstance();
      BeanUtils.populate(t, map);
      return t;
    } catch (Exception ex) {
      throw new RuntimeException(ex.getCause());
    }
  }

}
