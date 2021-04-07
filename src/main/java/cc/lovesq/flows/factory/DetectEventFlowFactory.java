package cc.lovesq.flows.factory;

import cc.lovesq.flows.definitions.EventFlow;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class DetectEventFlowFactory extends AbstractEventFlowFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        initBeans();
    }

    @Override
    public Map<String, EventFlow> getEventFlowBeans() {
        return applicationContext.getBeansOfType(EventFlow.class);
    }
}
