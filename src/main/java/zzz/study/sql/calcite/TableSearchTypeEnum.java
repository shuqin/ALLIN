package zzz.study.sql.calcite;

import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.Set;

/**
 * 表记录的查找类型
 * Created by qinshu on 2021/7/16
 */
@Getter
public enum TableSearchTypeEnum {

    FILTERABLE(Sets.newHashSet("process_event", "file")),
    HASHTABLE(Sets.newHashSet("hash"));

    private Set<String> tables;

    TableSearchTypeEnum(Set<String> tables) {
        this.tables = tables;
    }

    public static TableSearchTypeEnum get(String tablename) {
        for (TableSearchTypeEnum tste: TableSearchTypeEnum.values()) {
            if (tste.tables.contains(tablename)) {
                return tste;
            }
        }
        return FILTERABLE;
    }
}
