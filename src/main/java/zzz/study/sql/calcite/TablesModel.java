package zzz.study.sql.calcite;

import lombok.Data;

import java.util.List;

/**
 * 虚表定义模型
 * Created by qinshu on 2021/7/13
 */
@Data
public class TablesModel {

    private String version;
    private List<BizTable> tables;
}
