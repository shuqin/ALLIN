package cc.lovesq.model.event;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @Description TODO
 * @Date 2021/5/20 6:26 下午
 * @Created by qinshu
 */
@Getter
@Setter
public class AgentDetectEventData {

    @JsonProperty("rule_id")
    private String ruleId;

    @JsonProperty("format_output")
    private String formatOutput;

    @JsonProperty("info")
    private AgentDetectEventDetail info;

}
