package zzz.study.sql.calcite;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 * Created by qinshu on 2021/7/17
 */
@Data
public class ResultModel {

    private List<Object> col;

    public ResultModel() {
        col = new ArrayList<>();
    }

    public void add(Object value) {
        col.add(value);
    }

}
