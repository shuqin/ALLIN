package cc.lovesq.flows.detect.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description TODO
 * @Date 2021/4/7 9:22 下午
 * @Created by qinshu
 */
@Setter
@Getter
public class ThreatEvent {

    private Long id;
    private Integer agentGroup;
    private Integer createTime;
}
