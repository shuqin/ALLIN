package zzz.study.foundations.typeinfo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Description TODO
 * @Date 2021/5/14 1:10 下午
 * @Created by qinshu
 */
public class TypeUtils {

    public static String getParameterizedType(Class c) {
        Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) t;
            Type[] types = pt.getActualTypeArguments();
            if (types != null && types.length > 0) {
                return types[0].getTypeName();
            }
        }
        return null;
    }
}
