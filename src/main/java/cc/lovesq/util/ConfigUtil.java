package cc.lovesq.util;

import cc.lovesq.config.*;
import shared.utils.YamlUtil;
import zzz.study.function.refactor.StreamUtil;

import java.util.List;

/**
 * 配置文件解析
 */
public class ConfigUtil {

    public static EventFlowsModel loadFlows(String ymlFilePath) {
        return loadFlows(ymlFilePath, false);
    }

    public static EventFlowsModel loadFlows(String ymlFilePath, boolean isBrief) {
        return isBrief ? loadFlowsWithBrief(ymlFilePath) : load(ymlFilePath, EventFlowsModel.class);
    }

    private static EventFlowsModel loadFlowsWithBrief(String ymlFilePath) {
        EventFlowsModelRefCommon eventFlowsModelRefCommon = load(ymlFilePath, EventFlowsModelRefCommon.class);
        EventFlowsModel eventFlowsModel = new EventFlowsModel();
        eventFlowsModel.setVersion(eventFlowsModelRefCommon.getVersion());

        List<EventFlowExecutionModel> eventFlowExecutionModelList =
                StreamUtil.map(eventFlowsModelRefCommon.getFlows(), ConfigUtil::convert);
        eventFlowsModel.setFlows(eventFlowExecutionModelList);
        return eventFlowsModel;
    }

    private static EventFlowExecutionModel convert(EventFlowExecutionModelRefCommon efemrc) {
        CommonConfig cc = efemrc.getCommonConfig();
        EventFlowExecutionModel efem = new EventFlowExecutionModel();
        efem.setBizTypes(efemrc.getBizTypes());
        efem.setEventflowClassName(cc.getEventflowClassName());
        efem.setEventType(cc.getEventType());
        efem.setEventSourceType(cc.getEventSourceType());
        efem.setModel(cc.getModel());
        efem.setWay(cc.getWay());
        efem.setOriginParamType(cc.getOriginParamType());
        efem.setComponentParamType(cc.getComponentParamType());
        efem.setComponents(efemrc.getComponents());
        return efem;
    }

    /**
     * 通过 yml 文件加载组件执行模型对象
     */
    public static EventFlowExecutionModel load(String ymlFilePath) {
        return load(ymlFilePath, EventFlowExecutionModel.class);
    }

    /**
     * 通过 yml 文件加载对象模型
     */
    public static <T> T load(String ymlFilePath, Class<T> cls) {
        return YamlUtil.load(ymlFilePath, cls);
    }

}
