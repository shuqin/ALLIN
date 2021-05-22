package cc.lovesq.model.event;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @Description 入侵事件数据
 * @Date 2021/5/20 6:26 下午
 * @Created by qinshu
 */
@Getter
@Setter
public class AgentDetectEventData {

    @SerializedName("rule_id")
    @JsonProperty("rule_id")
    private String ruleId;

    @SerializedName("format_output")
    @JsonProperty("format_output")
    private String formatOutput;

    @SerializedName("info")
    @JsonProperty("info")
    private AgentDetectEventDetail info;

}
