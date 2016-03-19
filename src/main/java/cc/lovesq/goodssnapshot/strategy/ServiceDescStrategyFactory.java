package cc.lovesq.goodssnapshot.strategy;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description 服务快照逻辑的策略工厂
 * @Date 2021/1/2 3:21 下午
 * @Created by qinshu
 */
@Component
public class ServiceDescStrategyFactory implements ApplicationContextAware {

    private List<ServiceDescStrategy> serviceDescStrategies = new ArrayList<>();

    private ApplicationContext applicationContext;

    // 对新增开放，对修改关闭
    @PostConstruct
    public void init() {
        Map<String, ServiceDescStrategy> strategyMap = applicationContext.getBeansOfType(ServiceDescStrategy.class);

        serviceDescStrategies = new ArrayList<>(strategyMap.values());
    }

    public List<ServiceDescStrategy> getServiceDescStrategies() {
        return Collections.unmodifiableList(serviceDescStrategies);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
