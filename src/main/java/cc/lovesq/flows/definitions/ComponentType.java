package cc.lovesq.flows.definitions;

/**
 * 组件类型包括三要素：
 * 组件名称、组件业务类型、组件意图
 *
 * 组件编程涉及到一个问题：根据具体业务选择适合的组件。
 * 组件的选择目前依赖两个要素：组件的意图和组件业务类型
 *
 * ComponentType 与 FlowComponent 的定义保持一致
 * @see ComponentProperties
 */
public enum ComponentType {

    // 通用组件部分
    DefaultNotifySender("NotifySender", "common", "NotifySender"),
    DefaultEntitySaver("EntitySaver", "common", "EntitySaver"),
    DefaultBizEventSender("BizEventSender", "common", "BizEventSender"),
    DefaultBigDataSender("BigDataSender", "common", "BigDataSender"),
    DefaultThreatInfoSender("ThreatInfoSender", "common", "ThreatInfoSender"),

    // 定制业务组件部分

    ;

    String name;
    String biz;
    String purpose;

    ComponentType(String name, String biz, String purpose) {
        this.name = name;
        this.biz = biz;
        this.purpose = purpose;
    }

    public String getName() {
        return name;
    }

    public String getBiz() {
        return biz;
    }

    public String getPurpose() {
        return purpose;
    }

    public static String getKey(String purpose, String biz) {
        return purpose + "_" + biz;
    }
}
