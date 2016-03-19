package cc.lovesq.config;

import java.util.List;

/**
 * 带引用的事件处理流程配置
 * <p>
 * 引用 commonConfig 的引用配置
 */
public class EventFlowExecutionModelRefCommon {

    private List<String> bizTypes;

    private CommonConfig commonConfig;

    private List<String> components;

    public List<String> getBizTypes() {
        return bizTypes;
    }

    public void setBizTypes(List<String> bizTypes) {
        this.bizTypes = bizTypes;
    }

    public CommonConfig getCommonConfig() {
        return commonConfig;
    }

    public void setCommonConfig(CommonConfig commonConfig) {
        this.commonConfig = commonConfig;
    }

    public List<String> getComponents() {
        return components;
    }

    public void setComponents(List<String> components) {
        this.components = components;
    }
}
