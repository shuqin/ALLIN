package zzz.study.sql.calcite;

import cc.lovesq.util.ConfigUtil;

import java.util.List;

/**
 * 虚表定义
 * Created by qinshu on 2021/7/13
 */
public class TableDefinition {

    private static Database database = new Database();

    static {
        initTableDefinition();
    }

    private static void initTableDefinition() {

        TablesModel tableModel = ConfigUtil.load("tables.yml", TablesModel.class);
        for (BizTable table: tableModel.getTables()) {
            database.add(table);
        }
    }

    public static List<BizTable> getTables() {
        return database.getTables();
    }
}
