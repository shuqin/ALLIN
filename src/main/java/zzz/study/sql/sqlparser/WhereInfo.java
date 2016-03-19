package zzz.study.sql.sqlparser;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description SQL where 信息
 * @Date 2021/5/19 3:24 下午
 * @Created by qinshu
 */
@Getter
@Setter
public class WhereInfo {
    List<Condition> conditions;

    public WhereInfo() {
        conditions = new ArrayList<>();
    }

    public WhereInfo(List<Condition> expressions) {
        this.conditions = expressions;
    }

    public void addCondition(Condition c) {
        if (conditions == null) {
            conditions = new ArrayList<>();
        }
        conditions.add(c);
    }

    public void addCondition(String left, String right) {
        if (conditions == null) {
            conditions = new ArrayList<>();
        }
        conditions.add(new Condition(left, right));
    }
}
