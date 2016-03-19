package zzz.study.sql.calcite;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 数据转换器
 * Created by qinshu on 2021/7/9
 */
public class DataConverter {

    private static Map<String, Function<String, Object>> dataConvertFuncMap = new HashMap<>();

    static {
        dataConvertFuncMap.put("date", DateFormat::stringToDate);
        dataConvertFuncMap.put("tinyint", Byte::valueOf);
        dataConvertFuncMap.put("short", Short::valueOf);
        dataConvertFuncMap.put("smallint", Short::valueOf);
        dataConvertFuncMap.put("integer", Integer::valueOf);
        dataConvertFuncMap.put("long", Long::valueOf);
        dataConvertFuncMap.put("bigint", Long::valueOf);
        dataConvertFuncMap.put("double", Double::valueOf);
        dataConvertFuncMap.put("decimal", BigDecimal::new);
        dataConvertFuncMap.put("timestamp", strValue -> Long.valueOf(DateFormat.stringToMillis(strValue)));
        dataConvertFuncMap.put("float", Float::valueOf);
        dataConvertFuncMap.put("boolean", Boolean::valueOf);
    }

    public static Object convertCellValue(String strValue, String dataType) {
        if (strValue == null) {
            return null;
        }
        if ((strValue.equals("") || strValue.equals("\\N")) && !dataType.equals("string")) {
            return null;
        }
        return dataConvertFuncMap.getOrDefault(dataType, DataConverter::defaultValueOf).apply(strValue);
    }

    private static Object defaultValueOf(String strValue) {
        return strValue;
    }
}
