package zzz.study.sql.sqlparser.calcite;

import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;

import java.util.Map;

import static shared.stream.ListStream.stream;


/**
 * TODO
 * <p>
 * Created by qinshu on 2021/7/8
 */
public class MemorySchema extends AbstractSchema {

    private String name;

    public MemorySchema(String name) {
        this.name = name;
    }

    @Override
    protected Map<String, Table> getTableMap() {
        return stream(MemoryData.getTables()).toMap(BizTable::getTableName, t -> new MemoryTable(t));
    }
}
