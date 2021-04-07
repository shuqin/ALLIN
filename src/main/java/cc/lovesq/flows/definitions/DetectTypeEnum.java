package cc.lovesq.flows.definitions;

/**
 * @Description TODO
 * @Date 2021/4/7 8:48 下午
 * @Created by qinshu
 */
public enum DetectTypeEnum {

    BOUNCE_SHELL("bounce_shell", "反弹Shell"),

    ABNORMAL_LOGIN("abnormal_login", "异常登录"),


    LOCAL_RIGHTS("local_rights", "本地提权"),

    ;

    String type;
    String desc;

    DetectTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }
}
