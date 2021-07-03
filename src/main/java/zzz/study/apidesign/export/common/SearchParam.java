package zzz.study.apidesign.export.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SearchParam implements Serializable {

    /**
     * 业务归属ID，必传
     */
    private Long bizId;

    /**
     * 搜索起始时间，必传
     */
    private Long startTime;
    private Long endTime;

    /**
     * 扩展搜索入参，可选
     */
    private List<Condition> conditions;

}
