package zzz.study.function.basic;

import java.util.*;

/**
 * Created by shuqin on 17/3/30.
 * FieldConf 的伴生对象, 从Scala借鉴而来
 */
public class FieldConfAccompany {

    private static Map<String, FieldConf> fieldConfMap = new HashMap<String, FieldConf>();
    private static List<String> allFields = new ArrayList<>();

    static {
        for (FieldConf fc: FieldConf.values()) {
            fieldConfMap.put(fc.name(), fc);
            allFields.add(fc.getName());
        }
    }

    public static FieldConf getInstance(String name) {
        return fieldConfMap.get(name);
    }

    public static List<String> getAllFields() {
        return Collections.unmodifiableList(allFields);
    }
}
