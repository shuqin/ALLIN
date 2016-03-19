package zzz.study.sql.calcite;

import lombok.Data;

/**
 * PrepareStatement 参数对象
 * Created by qinshu on 2021/7/13
 */
@Data
public class PrepareStatementParamObject {

    private int index;
    private Object value;

    public PrepareStatementParamObject(int index, Object value) {
        this.index = index;
        this.value = value;
    }
}
