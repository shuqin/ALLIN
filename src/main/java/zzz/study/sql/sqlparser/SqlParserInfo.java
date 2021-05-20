package zzz.study.sql.sqlparser;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Date 2021/5/19 3:47 下午
 * @Created by qinshu
 */
@Setter
@Getter
public class SqlParserInfo {
    private List<TableInfo> tableInfos;
    private List<String> columns;
    private WhereInfo whereInfo;

    public SqlParserInfo() {
        tableInfos = new ArrayList<>();
        columns = new ArrayList<>();
    }

    public void addTableInfo(TableInfo tableInfo) {
        tableInfos.add(tableInfo);
    }

    public void addColumns(List<String> columns) {
        this.columns.addAll(columns);
    }

    public void setWhereInfo(WhereInfo whereInfo) {
        this.whereInfo = whereInfo;
    }

}
