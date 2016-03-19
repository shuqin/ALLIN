package cc.lovesq.model.event;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description TODO
 * @Date 2021/5/20 6:24 下午
 * @Created by qinshu
 */
@Getter
@Setter
public class AgentDetectEvent {

    private List<AgentDetectEventData> agentEventDatas;

}



