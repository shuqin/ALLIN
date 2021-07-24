package zzz.study.sql.calcite;

import lombok.Data;

/**
 * 谓词下推要过滤的值
 * Created by qinshu on 2021/7/15
 */
@Data
public class FilterValues {

    private int index;
    private Object value;

    public FilterValues(int index, Object value) {
        this.index = index;
        this.value = value;
    }
}
