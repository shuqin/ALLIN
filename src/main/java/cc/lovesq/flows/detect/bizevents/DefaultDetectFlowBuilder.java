package cc.lovesq.flows.detect.bizevents;

import cc.lovesq.flows.definitions.*;
import net.sf.json.JSONObject;

public class DefaultDetectFlowBuilder implements FlowContextBuilder<DetectEventDataWrapper> {

    @Override
    public AbstractFlowContext build(DetectEventDataWrapper eventData) {
        String detectType = eventData.getDetectType();

        Class<DetectDO> doCls = DetectEventEnum.getDoClass(detectType);
        DetectDO detectDO = parse(eventData.getData(), doCls);

        Class<DetectDTO> dtoCls = DetectEventEnum.getDtoClass(detectType);
        DetectDTO detectDTO = parse(eventData.getData(), dtoCls);

        return new DefaultDetectFlowContext(eventData, detectDO, detectDTO);
    }

    private <T> T parse(String text, Class<T> cls) {
        JSONObject jsonObject = JSONObject.fromObject(text);
        return (T)JSONObject.toBean(jsonObject, cls);
    }
}
