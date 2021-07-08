package zzz.study.sql.sqlparser.calcite;

import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaFactory;
import org.apache.calcite.schema.SchemaPlus;

import java.util.Map;

/**
 * TODO
 * <p>
 * Created by qinshu on 2021/7/8
 */
public class VirtualTableSchemaFactory implements SchemaFactory {

    @Override
    public Schema create(SchemaPlus schemaPlus, String name, Map<String, Object> map) {
        System.out.println("db: " + name);
        return new MemorySchema(name);
    }
}
