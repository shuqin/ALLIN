package cc.lovesq.flows.definitions;

import org.apache.commons.lang.StringUtils;
import cc.lovesq.flows.detect.eventflow.DefaultDetectEventFlower;

import java.util.*;

import static cc.lovesq.flows.definitions.ComponentType.DefaultBigDataSender;
import static cc.lovesq.flows.definitions.ComponentType.DefaultNotifySender;
import static cc.lovesq.flows.definitions.ComponentType.DefaultThreatInfoSender;
import static cc.lovesq.flows.definitions.DetectTypeEnum.*;

/**
 * 不同入侵事件处理流程的定义集中在这里管理
 *
 * 这样可以很清晰地知道一个入侵事件的整个处理流程，然后单个去看每个处理的内部是什么
 *
 * detectType 入侵事件类型，
 * @see cc.lovesq.flows.definitions.DetectTypeEnum
 */
public enum DetectEventFlowDefinitions {

    bounce_detect_flow(BOUNCE_SHELL.getType(),
            Arrays.asList(DefaultNotifySender, DefaultBigDataSender, DefaultThreatInfoSender)),

    abnormal_login_detect_flow(ABNORMAL_LOGIN.getType(),
            Arrays.asList(DefaultNotifySender, DefaultBigDataSender, DefaultThreatInfoSender)),

    local_rights_detect_flow(LOCAL_RIGHTS.getType(),
            Arrays.asList(DefaultNotifySender, DefaultBigDataSender)),

    ;

    /** 入侵事件业务类型 */
    String detectType;

    /** 入侵事件类型 */
    BizEventTypeEnum bizEventTypeEnum;

    /** 入侵事件处理流程的全限定类名 */
    String eventClassName;

    /** 事件处理所需要的组件 */
    List<ComponentType> componentTypes;

    DetectEventFlowDefinitions(String detectType, List<ComponentType> componentTypes) {
        this(detectType, BizEventTypeEnum.CREATE, DefaultDetectEventFlower.class.getName(), componentTypes);
    }

    DetectEventFlowDefinitions(String detectType, String eventClassName, List<ComponentType> componentTypes) {
        this(detectType, BizEventTypeEnum.CREATE, eventClassName, componentTypes);
    }

    DetectEventFlowDefinitions(String detectType, BizEventTypeEnum bizEventTypeEnum, String eventClassName, List<ComponentType> componentTypes) {
        this.detectType = detectType;
        this.bizEventTypeEnum = bizEventTypeEnum;
        this.eventClassName = eventClassName;
        this.componentTypes = componentTypes;
    }

    private static Map<String, DetectEventFlowDefinitions> flowDefinitionsMap = new HashMap<>();
    private static Set<String> flows = new HashSet<>();

    static {
        for (DetectEventFlowDefinitions detectEventFlowDefinitions : DetectEventFlowDefinitions.values()) {
            flowDefinitionsMap.put(getKey(detectEventFlowDefinitions.detectType, detectEventFlowDefinitions.bizEventTypeEnum.getType()), detectEventFlowDefinitions);
            flows.add(detectEventFlowDefinitions.detectType);
        }
    }

    public static String getKey(String detectModelType, String eventType) {
        return detectModelType + "_" + eventType;
    }

    public static String getEventFlowClassName(String detectModelType, String eventType) {
        return flowDefinitionsMap.get(getKey(detectModelType, eventType)).eventClassName;
    }

    public static List<ComponentType> getNeedComponents(String detectType) {
        return flowDefinitionsMap.get(getKey(detectType, BizEventTypeEnum.CREATE.getType())).componentTypes;
    }

    public static List<ComponentType> getNeedComponents(String detectType, String bizEventType) {
        if (StringUtils.isBlank(bizEventType)) {
            bizEventType = BizEventTypeEnum.CREATE.getType();
        }
        return flowDefinitionsMap.get(getKey(detectType, bizEventType)).componentTypes;
    }
}
