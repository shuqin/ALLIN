package cc.lovesq.flows.components;

import cc.lovesq.flows.definitions.AbstractFlowContext;
import cc.lovesq.flows.definitions.FlowComponent;
import cc.lovesq.flows.definitions.FlowDecision;
import cc.lovesq.flows.definitions.FlowResult;

public interface ThreatInfoSender<I extends AbstractFlowContext> extends FlowComponent<I> {
    void sendThreatMessage(I context);

    default FlowResult process(I context) {
        sendThreatMessage(context);
        return new FlowResult<>(null, FlowDecision.Continue);
    }
}
