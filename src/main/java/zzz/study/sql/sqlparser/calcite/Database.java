package zzz.study.sql.sqlparser.calcite;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 虚拟数据库
 * <p>
 * Created by qinshu on 2021/7/8
 */
public class Database {

    private List<BizTable> bizTables = new LinkedList<BizTable>();

    public void add(BizTable bizTable) {
        this.bizTables.add(bizTable);
    }

    public List<BizTable> getTables() {
        return Collections.unmodifiableList(bizTables);
    }

}
