package cc.lovesq.flows.detect.bizevents;


import cc.lovesq.flows.definitions.AbstractFlowContext;
import cc.lovesq.flows.definitions.DetectDO;
import cc.lovesq.flows.definitions.DetectDTO;

public class DefaultDetectFlowContext extends AbstractFlowContext<DetectEventDataWrapper> {

    public DefaultDetectFlowContext() {}

    public DefaultDetectFlowContext(DetectEventDataWrapper detectEventData,
                                    DetectDO detectDO,
                                    DetectDTO detectDTO) {
        super(detectEventData, detectDO, detectDTO);
    }

}
