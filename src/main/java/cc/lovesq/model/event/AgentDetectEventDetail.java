package cc.lovesq.model.event;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * @Description TODO
 * @Date 2021/5/20 6:27 下午
 * @Created by qinshu
 */
@Getter
@Setter
public class AgentDetectEventDetail {

    @JsonProperty("process_events")
    private ProcessEvent processEvent;

    @JsonProperty("proc_trees")
    private List<ProcessTree> procTree;

    @JsonProperty("containers")
    private Container container;

    @JsonProperty("sockets")
    private Socket socket;
}