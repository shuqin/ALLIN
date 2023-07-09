package shared.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射获取值
 * Created by qinshu on 2021/12/8
 */
public class ReflectionUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ReflectionUtil.class);

    private static final String GET_METHOD_PREFIX = "get";
    private static final String SET_METHOD_PREFIX = "set";

    public static Object getValue(Object obj, String expression) {
        try {
            return getValueByReflection(obj, expression);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            return null;
        }
    }

    public static void setValue(Object obj, String field, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field f = getField(obj.getClass(), field);
        f.setAccessible(true);
        f.set(obj, value);
        f.setAccessible(false);
    }

    /**
     * 反射获取对象 obj 的字段 field 的值
     */
    public static Object getValueByReflection(Object obj, String field)
            throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field f = getField(obj.getClass(), field);
        f.setAccessible(true);
        Method m = getMethod(obj.getClass(), ReflectionUtil.getterMethod(field));
        Object value = m.invoke(obj);
        f.setAccessible(false);
        return value;
    }

    public static String getterMethod(String field) {
        if (StringUtils.isBlank(field)) {
            return StringUtils.EMPTY;
        }
        return GET_METHOD_PREFIX + Character.toUpperCase(field.charAt(0)) + field.substring(1);
    }

    public static String setterMethod(String field) {
        if (StringUtils.isBlank(field)) {
            return StringUtils.EMPTY;
        }
        return SET_METHOD_PREFIX + Character.toUpperCase(field.charAt(0)) + field.substring(1);
    }

    private static Field getField(Class c, String field) throws NoSuchFieldException {
        try {
            return c.getDeclaredField(field);
        } catch (Exception ex) {
            return c.getSuperclass().getDeclaredField(field);
        }
    }

    private static Method getMethod(Class c, String method) throws NoSuchMethodException {
        try {
            return c.getDeclaredMethod(method);
        } catch (Exception ex) {
            return c.getSuperclass().getDeclaredMethod(method);
        }
    }
}