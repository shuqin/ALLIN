package zzz.study.sql.calcite;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import org.apache.calcite.schema.Function;
import org.apache.calcite.schema.ScalarFunction;
import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;
import org.apache.calcite.schema.impl.ScalarFunctionImpl;
import zzz.study.sql.calcite.functions.CalciteOperator;

import java.util.HashMap;
import java.util.Map;

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

    private static Map<String, Table> tableMapCache = new HashMap<>();

    @Override
    public Map<String, Table> getTableMap() {
        if (tableMapCache != null && tableMapCache.size() > 0) {
            return tableMapCache;
        }

        for (BizTable bt: TableDefinition.getTables()) {
            if (TableSearchTypeEnum.get(bt.getTableName()) == TableSearchTypeEnum.FILTERABLE) {
                tableMapCache.put(bt.getTableName(), new MemoryTable(bt));
            }
            else if (TableSearchTypeEnum.get(bt.getTableName()) == TableSearchTypeEnum.HASHTABLE) {
                tableMapCache.put(bt.getTableName(), new HashMemoryTable(bt));
            }
        }
        return tableMapCache;
    }

    @Override
    public Multimap<String, Function> getFunctionMultimap() {
        ImmutableMultimap<String, ScalarFunction> funcs = ScalarFunctionImpl.createAll(CalciteOperator.class);
        Multimap<String, Function> functions = HashMultimap.create();
        for(String key : funcs.keySet()) {
            for(ScalarFunction func : funcs.get(key)) {
                functions.put(key, func);
            }
        }

        return functions;
    }
}
