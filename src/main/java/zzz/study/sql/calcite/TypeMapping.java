package zzz.study.sql.calcite;

import org.apache.calcite.sql.type.SqlTypeName;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 类型处理
 * Created by qinshu on 2021/7/13
 */
public class TypeMapping {

    private static Map<String, SqlTypeName> SQLTYPE_MAPPING = new HashMap<String, SqlTypeName>();
    private static Map<String, Class> JAVATYPE_MAPPING = new HashMap<String, Class>();

    static {
        initJavaTypes();
    }

    private static void initJavaTypes() {
        SQLTYPE_MAPPING.put("char", SqlTypeName.CHAR);
        JAVATYPE_MAPPING.put("char", Character.class);
        SQLTYPE_MAPPING.put("varchar", SqlTypeName.VARCHAR);
        JAVATYPE_MAPPING.put("varchar", String.class);
        SQLTYPE_MAPPING.put("boolean", SqlTypeName.BOOLEAN);
        SQLTYPE_MAPPING.put("integer", SqlTypeName.INTEGER);
        JAVATYPE_MAPPING.put("integer", Integer.class);
        SQLTYPE_MAPPING.put("tinyint", SqlTypeName.TINYINT);
        SQLTYPE_MAPPING.put("smallint", SqlTypeName.SMALLINT);
        SQLTYPE_MAPPING.put("bigint", SqlTypeName.BIGINT);
        SQLTYPE_MAPPING.put("decimal", SqlTypeName.DECIMAL);
        SQLTYPE_MAPPING.put("numeric", SqlTypeName.DECIMAL);
        SQLTYPE_MAPPING.put("float", SqlTypeName.FLOAT);
        SQLTYPE_MAPPING.put("real", SqlTypeName.REAL);
        SQLTYPE_MAPPING.put("double", SqlTypeName.DOUBLE);
        SQLTYPE_MAPPING.put("date", SqlTypeName.DATE);
        JAVATYPE_MAPPING.put("date", Date.class);
        SQLTYPE_MAPPING.put("time", SqlTypeName.TIME);
        SQLTYPE_MAPPING.put("timestamp", SqlTypeName.TIMESTAMP);
        SQLTYPE_MAPPING.put("any", SqlTypeName.ANY);
    }

    public static Class getClass(String type) {
        return JAVATYPE_MAPPING.get(type);
    }

    public static SqlTypeName getSqlType(String type) {
        return SQLTYPE_MAPPING.get(type);
    }
}
