package cc.lovesq.flows.detect.eventflow;

import cc.lovesq.flows.definitions.DetectEventFlowDefinitions;
import cc.lovesq.flows.definitions.FlowComponent;
import cc.lovesq.flows.definitions.FlowContextBuilder;
import cc.lovesq.flows.detect.bizevents.DefaultDetectFlowBuilder;
import cc.lovesq.flows.detect.bizevents.DetectEventDataWrapper;
import cc.lovesq.flows.factory.FlowComponentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DefaultDetectEventFlower extends AbstractEventFlow<DetectEventDataWrapper> {

    private FlowContextBuilder flowContextBuilder = new DefaultDetectFlowBuilder();

    @Autowired
    public DefaultDetectEventFlower(FlowComponentFactory flowComponentFactory) {
        super(flowComponentFactory);
    }

    @Override
    public FlowContextBuilder builder() {
        return flowContextBuilder;
    }

    @Override
    public List<FlowComponent> flowComponents(String detectType) {
        return flowComponentFactory.getComponents(DetectEventFlowDefinitions.getNeedComponents(detectType));
    }
}
