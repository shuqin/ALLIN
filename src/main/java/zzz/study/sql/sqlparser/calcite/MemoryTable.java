package zzz.study.sql.sqlparser.calcite;

import org.apache.calcite.DataContext;
import org.apache.calcite.linq4j.AbstractEnumerable;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.Enumerator;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.ScannableTable;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.type.SqlTypeUtil;

/**
 * TODO
 * <p>
 * Created by qinshu on 2021/7/8
 */
public class MemoryTable extends AbstractTable implements ScannableTable {

    private BizTable bizTable;
    private RelDataType relDataType;

    public MemoryTable(BizTable bizTable) {
        this.bizTable = bizTable;
    }

    @Override
    public Enumerable<Object[]> scan(DataContext dataContext) {

        return new AbstractEnumerable<Object[]>() {
            @Override
            public Enumerator<Object[]> enumerator() {

                return new MemoryEnumerator(bizTable.getFields(), bizTable.getColumnTypes(), bizTable.getData());
            }
        };
    }

    @Override
    public RelDataType getRowType(RelDataTypeFactory typeFactory) {
        if(relDataType == null) {
            RelDataTypeFactory.FieldInfoBuilder fieldInfo = typeFactory.builder();
            for (Column column : this.bizTable.getColumns()) {
                RelDataType sqlType = typeFactory.createJavaType(
                        MemoryData.getClass(column.getType()));
                sqlType = SqlTypeUtil.addCharsetAndCollation(sqlType, typeFactory);
                fieldInfo.add(column.getName(), sqlType);
            }
            this.relDataType = typeFactory.createStructType(fieldInfo);
        }
        return this.relDataType;
    }
}
