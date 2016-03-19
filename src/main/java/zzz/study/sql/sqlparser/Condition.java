package zzz.study.sql.sqlparser;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description SQL条件
 * @Date 2021/5/19 5:08 下午
 * @Created by qinshu
 */
@Setter
@Getter
public class Condition {
    private String left;
    private String right;

    public Condition(String left, String right) {
        this.left = left;
        this.right = right;
    }
}
