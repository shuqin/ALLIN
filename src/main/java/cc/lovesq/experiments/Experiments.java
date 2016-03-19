package cc.lovesq.experiments;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Experiments implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    private static Logger logger = LoggerFactory.getLogger(Experiments.class);

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent applicationEvent) {
        doExperiments();
    }

    public void doExperiments() {

        Map<String, IExperiment> experimentBeans = applicationContext.getBeansOfType(IExperiment.class);
        experimentBeans.forEach(
                (name, bean) -> {
                    logger.info(name);
                    bean.test();
                }

        );

    }
}
