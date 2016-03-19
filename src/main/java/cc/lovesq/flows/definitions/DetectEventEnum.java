package cc.lovesq.flows.definitions;

import cc.lovesq.flows.detect.model.*;

import java.util.HashMap;
import java.util.Map;

import static cc.lovesq.flows.definitions.BigDataSenderContants.*;
import static cc.lovesq.flows.definitions.DetectTypeEnum.*;

/**
 * 入侵事件相关联的 DTO 及通知、大数据发送等配置
 */
public enum DetectEventEnum {

    BounceShell(BOUNCE_SHELL.getType(), BounceShell.class, BounceShellDTO.class, "bounceshell", BOUNCE_SHELL_BIG_DATA_TYPE),
    AbnormalLogin(ABNORMAL_LOGIN.getType(), BaseAbnormalLogin.class, BaseAbnormalLoginDTO.class, "abnormallogin", ABNORMAL_LOGIN_BIG_DATA_TYPE),
    LocalRights(LOCAL_RIGHTS.getType(), LocalRights.class, LocalRights.class, "localrights", LOCAL_RIGHTS_BIG_DATA_TYPE),


    ;

    private static Map<String, DetectEventEnum> notifyMap = new HashMap<>();

    static {
        for (DetectEventEnum de : DetectEventEnum.values()) {
            notifyMap.put(de.detectType, de);
        }
    }

    String detectType;
    Class doCls;
    Class dtoCls;
    String notifyType;
    String bigDataType;

    DetectEventEnum(String detectType, Class doCls, Class dtoCls, String notifyType, String bigDataType) {
        this.detectType = detectType;
        this.doCls = doCls;
        this.dtoCls = dtoCls;
        this.notifyType = notifyType;
        this.bigDataType = bigDataType;
    }

    public static String getNotifyType(String detectType, int osType) {
        return getNotifyType(detectType);
    }

    public static String getNotifyType(String detectType) {
        return notifyMap.get(detectType).notifyType;
    }

    public static Class getDoClass(String detectType) {
        return notifyMap.get(detectType).doCls;
    }

    public static Class getDtoClass(String detectType) {
        return notifyMap.get(detectType).dtoCls;
    }

    public static String getBigDataType(String detectType) {
        return notifyMap.get(detectType).bigDataType;
    }
}
