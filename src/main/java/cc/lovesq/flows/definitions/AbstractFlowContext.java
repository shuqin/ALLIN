package cc.lovesq.flows.definitions;

import java.util.Map;

public abstract class AbstractFlowContext<E extends EventData> {

    /** 事件对象 */
    protected E eventData;

    /** 事件对象中所含的业务 DO 对象 */
    protected DetectDO detectDO;

    /** 事件对象中所含的业务 DTO 对象 */
    protected DetectDTO detectDTO;

    /** 扩展信息 */
    private Map<String, Holder> extendInfo;

    public AbstractFlowContext() {
    }

    public AbstractFlowContext(E eventData, DetectDO detectDO, DetectDTO detectDTO) {
        this.eventData = eventData;
        this.detectDO = detectDO;
        this.detectDTO = detectDTO;
    }

    public E getEventData() {
        return eventData;
    }

    public String getData() {
        return eventData.getData();
    }

    public DetectDO getDetectDO() {
        return detectDO;
    }

    public DetectDTO getDetectDTO() {
        return detectDTO;
    }

    public <T> void put(String name, Holder<T> holder) {
        extendInfo.put(name, holder);
    }

    public <T> T get(String name, Class<T> cls) {
        return (T)extendInfo.get(name).getData();
    }


}
