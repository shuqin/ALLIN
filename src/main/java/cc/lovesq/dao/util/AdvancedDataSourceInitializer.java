package cc.lovesq.dao.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

@SuppressWarnings("rawtypes")
public class AdvancedDataSourceInitializer implements ApplicationListener, ApplicationContextAware {

    private String                      desiredEventClassName;
    protected ApplicationContext        applicationContext;

    public void onApplicationEvent(ApplicationEvent event) {
        if (shouldStart(event)) {

        }
    }
    
   
    protected Class<?> getDesiredType() {
        try {
            return Class.forName(desiredEventClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String getDesiredEventClassName() {
        return desiredEventClassName;
    }

    public void setDesiredEventClassName(String desiredEventClassName) {
        this.desiredEventClassName = desiredEventClassName;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected boolean shouldStart(ApplicationEvent event) {
        Class<?> clazz = getDesiredType();
        return clazz.isInstance(event);
    }
}
