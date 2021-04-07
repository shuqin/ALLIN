package cc.lovesq.flows.factory;


import cc.lovesq.flows.definitions.DetectEventFlowDefinitions;
import cc.lovesq.flows.definitions.EventFlow;
import cc.lovesq.flows.detect.bizevents.DetectEventDataWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 可定制的事件处理流程Bean实例的工厂类
 *
 * 用来生成和定位事件处理流程实例
 *
 * Usage:
 *
 * <pre> {@code
 *
 * @Component
 * public class DetectEventFlowFactory extend AbstractEventFlowFactory implements ApplicationContextAware {
 *
 *    public  DetectEventFlowFactory() {}
 *
 *    private ApplicationContext applicationContext;
 *
 *    @Override
 *    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
 *         this.applicationContext = applicationContext;
 *    }
 *
 *    @PostConstruct
 *    public void init() {
 *         initBeans();
 *    }
 *
 *    public Map<String, IEventFlow> getEventFlowBeans() {
 *        return applicationContext.getBeansOfType(IEventFlow.class);
 *    }
 * }
 *
 * </pre>
 *
 * NOTE:
 * eventflow-lib 为了尽量减少外部依赖(不依赖 SpringIoC)，因此这里只支持基本能力，不能直接做成可使用的成品
 *
 */
public abstract class AbstractEventFlowFactory {

    private final Logger logger = LoggerFactory.getLogger(AbstractComponentFactory.class);

    protected Map<String, EventFlow> eventFlowMap = new HashMap<>();

    private volatile boolean initialized = false;

    /*
     *  初始化事件处理流程Beans
     *
     *  eventFlowBeans 推荐以下方式来获取：
     *  applicationContext.getBeansOfType(IEventFlow.class);
     *
     */
    protected void initBeans() {
        if (!initialized) {
            Map<String, EventFlow> eventFlowBeans = getEventFlowBeans();
            for (EventFlow eventFlow: eventFlowBeans.values()) {
                eventFlowMap.put(eventFlow.getClass().getName(), eventFlow);
            }
            logger.info("init-success: eventFlowMap: {}", eventFlowMap);
            initialized = true;
        }
    }

    /** 返回的事件处理流程 bean 映射 Map[BeanName,IEventFlowBean] */
    abstract public Map<String, EventFlow> getEventFlowBeans();

    /**
     * 根据指定的事件处理流程全限定性类名获取指定的 Bean 实例
     */
    public EventFlow get(String eventFlowClassName) {
        return eventFlowMap.get(eventFlowClassName);
    }

    public EventFlow getEventFlow(String bizEventType, String detectType) {
        String eventFlowClassName = DetectEventFlowDefinitions.getEventFlowClassName(detectType, bizEventType);
        return eventFlowMap.get(eventFlowClassName);
    }
}

