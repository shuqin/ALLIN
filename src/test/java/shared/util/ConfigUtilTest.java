package shared.util;

import cc.lovesq.CommonForTest;
import cc.lovesq.config.EventFlowExecutionModel;
import cc.lovesq.config.EventFlowsModel;
import cc.lovesq.config.ExecWay;
import cc.lovesq.config.ExecutionModel;
import cc.lovesq.util.ConfigUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * YML 配置加载
 */
public class ConfigUtilTest extends CommonForTest {

    @Test
    public void testLoad() {
        EventFlowExecutionModel eventFlowExecModel = ConfigUtil.load("eventflow_finished.yml");
        eq(Arrays.asList("docker_bounce_shell"), eventFlowExecModel.getBizTypes());
        eq("cc.lovesq.eventflow.demo.DefaultEventFlow", eventFlowExecModel.getEventflowClassName());
        eq(ExecutionModel.eventflow, eventFlowExecModel.getModel());
        eq(ExecWay.serial, eventFlowExecModel.getWay());
        eq("cc.lovesq.eventflow.demo.DefaultEventData", eventFlowExecModel.getOriginParamType());
        eq("cc.lovesq.eventflow.demo.DefaultFlowContext", eventFlowExecModel.getComponentParamType());
        eq(Arrays.asList("notificationSender_common", "defaultBigDataSender", "cc.lovesq.eventflow.demo.components.DefaultBigDataSender"), eventFlowExecModel.getComponents());
    }

    @Test
    public void testLoadEventflows() {
        EventFlowsModel eventFlowsModel = ConfigUtil.loadFlows("eventflows.yml");
        test(eventFlowsModel);
    }

    @Test
    public void testLoadEventflowsBrief() {
        EventFlowsModel eventFlowsModel = ConfigUtil.loadFlows("eventflows_brief.yml", true);
        test(eventFlowsModel);
    }

    private void test(EventFlowsModel eventFlowsModel) {
        eq("1.0", eventFlowsModel.getVersion());

        List<EventFlowExecutionModel> eventFlowExecutionModelList = eventFlowsModel.getFlows();
        eq(2, eventFlowExecutionModelList.size());

        EventFlowExecutionModel first = eventFlowExecutionModelList.get(0);
        eq(Arrays.asList("docker_bounce_shell", "abnormal_login"), first.getBizTypes());
        eq("create", first.getEventType());
        eq("bizEvent", first.getEventSourceType());
        eq("cc.lovesq.eventflow.demo.DefaultEventFlow", first.getEventflowClassName());
        eq("cc.lovesq.eventflow.demo.DefaultEventData", first.getOriginParamType());
        eq(Arrays.asList("notificationSender_common", "defaultBigDataSender", "cc.lovesq.eventflow.demo.components.DefaultBigDataSender"), first.getComponents());

        EventFlowExecutionModel second = eventFlowExecutionModelList.get(1);
        eq(Arrays.asList("local_rights", "docker_local_rights"), second.getBizTypes());
        eq("cc.lovesq.eventflow.demo.DefaultEventFlow", second.getEventflowClassName());
        eq("cc.lovesq.eventflow.demo.DefaultEventData", second.getOriginParamType());
        eq(Arrays.asList("notificationSender_common", "defaultBigDataSender"), second.getComponents());
    }
}
