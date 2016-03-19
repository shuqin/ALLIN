package cc.lovesq.flows.executor;

import cc.lovesq.flows.definitions.*;
import cc.lovesq.flows.factory.FlowComponentFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 组件执行器
 */
@Component
public class ComponentExecutor {

    @Resource
    private FlowComponentFactory flowComponentFactory;

    // 如果要支持并发，这里可以有一个组件执行线程池

    /**
     * 执行指定流程组件集，若任一组件要结束流程，则流程结束
     */
    public <I extends AbstractFlowContext> FlowResult execMayExit(I flowContext, List<FlowComponent> flowComponents) {
        for (FlowComponent flowComp : flowComponents) {
            if (!flowComp.needAccess(flowContext)) {
                continue;
            }
            FlowResult flowResult = flowComp.process(flowContext);
            if (flowResult.getFlowDecision() == null || FlowDecision.Termination.equals(flowResult.getFlowDecision())) {
                return FlowResultHelper.terminateResult();
            }
        }
        return FlowResultHelper.continueResult();
    }

    /**
     * 单个普通业务组件执行
     */
    public <I extends AbstractFlowContext> FlowResult exec(I param, FlowComponent<I> c) {
        if (c.needAccess(param)) {
            return c.process(param);
        }
        return FlowResultHelper.continueResult();
    }

    public <I extends AbstractFlowContext> FlowResult exec(I context, String compClassName) {
        FlowComponent c = flowComponentFactory.getComponent(compClassName);
        return exec(context, c);
    }

    /**
     * 任意多个流程组件执行(提供更灵活的组件执行能力)
     * 任一组件不影响后续组件的执行(抛出异常除外)
     * <p>
     * NOTE: 这些业务组件的参数是一样的
     */
    public <I extends AbstractFlowContext> FlowResult execBatch(I context, List<FlowComponent> components) {
        for (FlowComponent c : components) {
            if (c.needAccess(context)) {
                c.process(context);
            }
        }
        return FlowResultHelper.continueResult();
    }

}
