package cc.lovesq.model.event;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * @Description 入侵事件详情对象
 * @Date 2021/5/20 6:27 下午
 * @Created by qinshu
 */
@Getter
@Setter
public class AgentDetectEventDetail {

    @SerializedName("process_events")
    @JsonProperty("process_events")
    private ProcessEvent processEvent;

    @SerializedName("proc_trees")
    @JsonProperty("proc_trees")
    private List<ProcessTree> procTree;

    @SerializedName("containers")
    @JsonProperty("containers")
    private Container container;

    @SerializedName("sockets")
    @JsonProperty("sockets")
    private Socket socket;
}