package zzz.study.sql.calcite;

import lombok.Data;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static shared.stream.ListStream.stream;

/**
 * 虚拟表
 *
 * Created by qinshu on 2021/7/8
 */
@Data
public class BizTable {

    private String tableName;

    private List<Column> columns = new LinkedList<Column>();

    public BizTable() {}

    // 虚拟表的列定义缓存
    private static Map<String, List<Column>> columnsMap = new ConcurrentHashMap<>();

    public BizTable(String tableName, List<Column> columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    public String getTableName() {
        return tableName;
    }

    public List<Column> getColumns() {
        if (columnsMap.get(tableName) == null) {
            columnsMap.put(tableName, Collections.unmodifiableList(columns));
        }
        return columnsMap.get(tableName);
    }

    public List<String> getColumnTypes() {
        return stream(getColumns()).map(Column::getType);
    }

    public int getFieldCount() {
        return columns.size();
    }

}
