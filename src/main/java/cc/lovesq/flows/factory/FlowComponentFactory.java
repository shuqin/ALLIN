package cc.lovesq.flows.factory;

import cc.lovesq.flows.definitions.FlowComponent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class FlowComponentFactory extends AbstractComponentFactory implements ApplicationContextAware {

    private static Log log = LogFactory.getLog(FlowComponentFactory.class);

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
    public Map<String, FlowComponent> getComponentBeans() {
        return applicationContext.getBeansOfType(FlowComponent.class);
    }
}
