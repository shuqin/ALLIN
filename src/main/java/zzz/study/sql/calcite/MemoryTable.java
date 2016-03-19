package zzz.study.sql.calcite;

import org.apache.calcite.DataContext;
import org.apache.calcite.linq4j.AbstractEnumerable;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.Enumerator;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.rex.RexCall;
import org.apache.calcite.rex.RexInputRef;
import org.apache.calcite.rex.RexLiteral;
import org.apache.calcite.rex.RexNode;
import org.apache.calcite.schema.FilterableTable;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.type.SqlTypeUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 * <p>
 * Created by qinshu on 2021/7/8
 */
public class MemoryTable extends AbstractTable implements FilterableTable {

    private BizTable bizTable;
    private RelDataType relDataType;

    public MemoryTable(BizTable bizTable) {
        this.bizTable = bizTable;
    }

    @Override
    public Enumerable<Object[]> scan(DataContext dataContext, List<RexNode> filters) {

        // 获取谓词，比如 $index = 'Docker-Detect-Event-1626236000'
        // 对于 table = process_event, index = 3 ; table = file , index = 2

        final String[] filterValues = new String[bizTable.getColumnTypes().size()];
        for (final Iterator<RexNode> i = filters.iterator(); i.hasNext();) {
            final RexNode filter = i.next();
            if (addFilter(filter, filterValues)) {
                i.remove();
            }
        }

        List<FilterValues> filterValueList = new ArrayList<>();
        for (int i=0; i < filterValues.length; i++) {
            if (filterValues[i] != null) {
                filterValueList.add(new FilterValues(i, filterValues[i]));
            }
        }

        // 根据获取的谓词提前过滤表数据
        List<List<String>> tableData = MemoryData.getData(bizTable.getTableName());
        List<List<String>> tableDataFiltered = tableData.stream().filter(td -> filter(td, filterValueList)).collect(Collectors.toList());

        return new AbstractEnumerable<Object[]>() {
            @Override
            public Enumerator<Object[]> enumerator() {
                return new MemoryEnumerator(bizTable.getFieldCount(), bizTable.getColumnTypes(), tableDataFiltered);
            }
        };
    }

    private boolean filter(List<String> td, List<FilterValues> filterValuesList) {
        if (filterValuesList.size() > 0) {
            return td.get(filterValuesList.get(0).getIndex()).equals(filterValuesList.get(0).getValue());
        }
        return true;
    }

    @Override
    public RelDataType getRowType(RelDataTypeFactory typeFactory) {
        if(relDataType == null) {
            RelDataTypeFactory.FieldInfoBuilder fieldInfo = typeFactory.builder();
            for (Column column : this.bizTable.getColumns()) {
                RelDataType sqlType = typeFactory.createJavaType(
                        TypeMapping.getClass(column.getType()));
                sqlType = SqlTypeUtil.addCharsetAndCollation(sqlType, typeFactory);
                fieldInfo.add(column.getName(), sqlType);
            }
            this.relDataType = typeFactory.createStructType(fieldInfo);
        }
        return this.relDataType;
    }

    private boolean addFilter(RexNode filter, Object[] filterValues) {
        if (filter.isA(SqlKind.EQUALS)) {
            final RexCall call = (RexCall) filter;
            RexNode left = call.getOperands().get(0);
            if (left.isA(SqlKind.CAST)) {
                left = ((RexCall) left).operands.get(0);
            }
            final RexNode right = call.getOperands().get(1);
            if (left instanceof RexInputRef
                    && right instanceof RexLiteral) {
                final int index = ((RexInputRef) left).getIndex();
                if (filterValues[index] == null) {
                    filterValues[index] = ((RexLiteral) right).getValue2().toString();
                    return true;
                }
            }
        }
        return false;
    }
}
