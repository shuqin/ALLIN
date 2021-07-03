package cc.lovesq.flows.factory;

import cc.lovesq.flows.definitions.ComponentProperties;
import cc.lovesq.flows.definitions.ComponentType;
import cc.lovesq.flows.definitions.FlowComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zzz.study.function.refactor.StreamUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 可定制的组件工厂类
 */
public abstract class AbstractComponentFactory {

    private static Logger logger = LoggerFactory.getLogger(AbstractComponentFactory.class);

    Map<String, FlowComponent> componentBizMap = new HashMap<>();
    Map<String, FlowComponent> componentClassMap = new HashMap<>();

    private volatile boolean initialized = false;

    /*
     * 初始化组件Beans
     */
    protected void initBeans() {
        if (!initialized) {
            Map<String, FlowComponent> beansOfType = getComponentBeans();
            beansOfType.values().forEach(
                    component -> {
                        ComponentProperties annotations = component.getClass().getAnnotation(ComponentProperties.class);
                        if (annotations != null) {
                            componentBizMap.put(ComponentType.getKey(annotations.purpose(), annotations.biz()), component);
                        }
                        componentClassMap.put(component.getClass().getName(), component);
                    }
            );
            logger.info("init-success: ComponentBizMap: {}", componentBizMap);
            logger.info("init-success: componentClassMap: {}", componentClassMap);
            initialized = true;
        }
    }

    /**
     * 返回所有对应的组件实例映射 Map[BeanName, ComponentBean]
     * <p>
     * ComponentBeans 推荐以下方式获取：
     * applicationContext.getBeansOfType(Component.class);
     */
    abstract public Map<String, FlowComponent> getComponentBeans();

    /**
     * 根据指定的组件全限定性类名来获取对应的组件实例
     *
     * @param qualifiedClassName 组件全限定性类名
     * @return 组件实例
     */
    public FlowComponent getComponent(String qualifiedClassName) {
        return componentClassMap.get(qualifiedClassName);
    }

    /**
     * 根据指定的组件意图和业务类型来获取对应的组件实例
     *
     * @param purpose 组件意图标识
     * @param biz     组件业务类型
     * @return 组件实例
     */
    public FlowComponent getComponent(String purpose, String biz) {
        return componentBizMap.get(ComponentType.getKey(purpose, biz));
    }

    /**
     * 根据指定的组件类型来获取对应的组件实例
     *
     * @param componentType 组件类型
     * @return 组件实例
     */
    public FlowComponent getComponent(ComponentType componentType) {
        return componentBizMap.get(ComponentType.getKey(componentType.getPurpose(), componentType.getBiz()));
    }

    /**
     * 根据组件类型集合批量获取对应的组件实例集合
     *
     * @param componentTypeList 组件类型集合
     * @return 组件实例集合
     */
    public List<FlowComponent> getComponents(List<ComponentType> componentTypeList) {
        return StreamUtil.map(componentTypeList, com -> getComponent(com.getPurpose(), com.getBiz()));
    }
}
