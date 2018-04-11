package zzz.study.annotations;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * Created by shuqin on 18/4/11.
 */
public interface DomainSearch {

  Log logger = LogFactory.getLog(DomainSearch.class);

  default String toEsQuery() {
    Object customerDomain = this;
    EsQuery esQuery = new EsQuery();
    Field[] fields = this.getClass().getDeclaredFields();
    for (Field f: fields) {
      try {
        if (Modifier.isStatic(f.getModifiers())) {
          continue;
        }

        f.setAccessible(true);

        Object value = f.get(customerDomain);

        if (f.getAnnotation(EsField.class) != null) {
          EsField field = f.getAnnotation(EsField.class);

          if (field.required() && value == null) {
            throw new RuntimeException("field '" + field + "' is required. value is null");
          }

          if (isNeedOmitted(value)) {
            f.setAccessible(false);
            continue;
          }

          if ((value instanceof List) && ((List)value).size() == 1) {
            // 针对 List 中单个值做优化查询
            esQuery = esQuery.addTermFilter(field.name(), ((List)value).get(0));
          }
          else {
            esQuery = esQuery.addTermFilter(field.name(), value);
          }
        }

        f.setAccessible(false);

      } catch (Exception ex) {
        logger.error("failed to build es query for field: " + f.getName(), ex);
        throw new RuntimeException(ex.getCause());
      }
    }
    return esQuery.toJsonString();
  }

  /**
   * 判断是否需要忽略该字段的查询
   * @param value 字段值
   * @return 是否要忽略
   */
  default boolean isNeedOmitted(Object value) {
    if (value == null) {
      return true;
    }

    // 空字符串搜索值忽略
    if ((value instanceof String) && StringUtils.isBlank(value.toString())) {
      return true;
    }

    // 空列表串忽略
    if ((value instanceof List) && ((List)value).isEmpty()) {
      return true;
    }
    return false;
  }

}
