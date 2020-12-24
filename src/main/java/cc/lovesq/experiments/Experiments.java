package cc.lovesq.experiments;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@Component
public class Experiments implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    private static Log log = LogFactory.getLog(Experiments.class);

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
                    log.info(name);
                    bean.test();
                }

        );

    }
}
