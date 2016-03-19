package cc.lovesq.flows.detect.eventflow;


import cc.lovesq.flows.definitions.*;
import cc.lovesq.flows.executor.ComponentExecutor;
import cc.lovesq.flows.factory.FlowComponentFactory;

import javax.annotation.Resource;
import java.util.List;

public abstract class AbstractEventFlow<E extends EventData> implements EventFlow<E> {

    @Resource
    protected FlowComponentFactory flowComponentFactory;
    @Resource
    protected ComponentExecutor componentExecutor;

    public AbstractEventFlow(FlowComponentFactory flowComponentFactory) {
        this.flowComponentFactory = flowComponentFactory;
    }

    @Override
    public void process(E eventData) {

        AbstractFlowContext flowContext = builder().build(eventData);
        List<FlowComponent> flowComponents = flowComponents(eventData.getType());
        componentExecutor.execMayExit(flowContext, flowComponents);
    }

    /**
     * 可以覆写此方法来构建自己的 FlowContext
     */
    abstract public FlowContextBuilder builder();

    /**
     * 可以覆写此方法来指明流程所需要的业务处理组件
     */
    abstract public List<FlowComponent> flowComponents(String detectType);
}
