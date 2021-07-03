package shared.utils;

import java.lang.reflect.Field;

/**
 * @Description 反射获取字段的值
 * @Date 2021/5/20 9:30 下午
 * @Created by qinshu
 */
public class ReflectionUtil {

    public static Object getValue(Object obj, String field) {
        Field[] fields = obj.getClass().getDeclaredFields();
        Object value = null;
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.getName().equals(field)) {
                try {
                    value = f.get(obj);
                } catch (IllegalAccessException e) {
                    // log
                }
            }
            f.setAccessible(false);
        }
        return value;
    }
}
