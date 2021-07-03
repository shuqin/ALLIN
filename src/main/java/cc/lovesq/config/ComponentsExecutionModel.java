package cc.lovesq.config;

import java.util.List;

/**
 * 有序组件集执行模型
 */
public class ComponentsExecutionModel {

    /**
     * serial 串行(不指定默认)  parallel 并发
     */
    protected ExecWay way = ExecWay.serial;

    /**
     * 组件参数类型
     */
    protected String componentParamType;

    protected List<String> components;

    public ExecWay getWay() {
        return way;
    }

    public void setWay(ExecWay execWay) {
        this.way = execWay;
    }

    public String getComponentParamType() {
        return componentParamType;
    }

    public void setComponentParamType(String componentParamType) {
        this.componentParamType = componentParamType;
    }

    public List<String> getComponents() {
        return components;
    }

    public void setComponents(List<String> components) {
        this.components = components;
    }


}
