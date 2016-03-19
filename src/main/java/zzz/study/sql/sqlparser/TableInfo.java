package zzz.study.sql.sqlparser;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description SQL 表信息
 * @Date 2021/5/19 3:09 下午
 * @Created by qinshu
 */
@Getter
@Setter
public class TableInfo {

    private String name;
    private String alias;

    public TableInfo(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }
}
