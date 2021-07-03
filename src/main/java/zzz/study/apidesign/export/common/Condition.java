package zzz.study.apidesign.export.common;

import lombok.Data;
import zzz.study.patterns.composite.escondition.Match;
import zzz.study.patterns.composite.escondition.Op;
import zzz.study.patterns.composite.escondition.Range;

import java.io.Serializable;

@Data
public class Condition implements Serializable {

    private static final long serialVersionUID = 7375091182172384776L;

    /**
     * ES 字段
     */
    private String fieldName;

    /**
     * 操作符
     */
    private Op op;

    /**
     * 参数值
     */
    private Object value;

    /**
     * 范围对象传参
     */
    private Range range;

    /**
     * 匹配对象传参
     */
    private Match match;

    // 为了让JsonMap 能走通，必须有一个默认构造器
    public Condition() {
    }

    public Condition(String fieldName, Op op, Object value) {
        this.fieldName = fieldName;
        this.op = op;
        this.value = value;
    }

}
