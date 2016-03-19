package cc.lovesq.flows.definitions;

/**
 * @Description 事件类型
 * @Date 2021/4/7 8:51 下午
 * @Created by qinshu
 */
public enum BizEventTypeEnum {

    CREATE("create", "创建事件"),
    UPDATE("update", "更新事件"),

    ;

    String type;
    String desc;

    BizEventTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }
}
