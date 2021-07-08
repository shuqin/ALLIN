package zzz.study.sql.sqlparser.calcite;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static shared.stream.ListStream.stream;

/**
 * 虚拟表
 *
 * Created by qinshu on 2021/7/8
 */
public class BizTable {

    private String tableName;

    private List<Column> columns = new LinkedList<Column>();

    private List<List<String>> data = new LinkedList<List<String>>();

    public BizTable(String tableName, List<Column> columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    public String getTableName() {
        return tableName;
    }

    public List<Column> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    public int[] getFields() {
        int n = columns.size();
        int[] integers = new int[n];
        for (int i = 0; i < n; i++) {
            integers[i] = i;
        }
        return integers;
    }

    public List<String> getColumnTypes() {
        return stream(columns).map(Column::getType);
    }

    public List<List<String>> getData() {
        return data;
    }

    public void add(List<String> record) {
        data.add(record);
    }

    public void addAll(List<List<String>> records) {
        data.addAll(records);
    }

}
