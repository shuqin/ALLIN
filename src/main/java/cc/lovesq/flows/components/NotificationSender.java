package cc.lovesq.flows.components;

import cc.lovesq.flows.definitions.AbstractFlowContext;
import cc.lovesq.flows.definitions.FlowComponent;
import cc.lovesq.flows.definitions.FlowDecision;
import cc.lovesq.flows.definitions.FlowResult;

public interface NotificationSender<I extends AbstractFlowContext> extends FlowComponent<I> {
    void sendNotify(I context);

    default FlowResult process(I context) {
        sendNotify(context);
        return new FlowResult(null, FlowDecision.Continue);
    }
}
