package cc.lovesq.service;

import cc.lovesq.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import zzz.study.foundations.typeinfo.TypeUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Date 2021/5/14 10:53 上午
 * @Created by qinshu
 */
@Component
public class BaseServiceFactory implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(BaseServiceFactory.class);

    private ApplicationContext applicationContext;

    private Map<String, BaseServiceImpl> doServiceMap = new HashMap<>();

    @PostConstruct
    public void init() {
        Map<String, BaseServiceImpl> baseServiceMap = applicationContext.getBeansOfType(BaseServiceImpl.class);
        for (BaseServiceImpl bs: baseServiceMap.values()) {
            Class c = bs.getClass();
            // 解决cglib动态代理问题
            if (bs.getClass().getName().contains("CGLIB")) {
                c = bs.getClass().getSuperclass();
            }
            String typeName = TypeUtils.getParameterizedType(c);
            doServiceMap.put(typeName, bs);

        }
        logger.info("doServiceMap: {}", doServiceMap);
    }

    public BaseService getService(String doClassName) {
        return doServiceMap.get(doClassName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
