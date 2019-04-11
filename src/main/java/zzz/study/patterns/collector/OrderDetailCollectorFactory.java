package zzz.study.patterns.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

import static cc.lovesq.exception.Errors.ServerError;

@Component("orderDetailCollectorFactory")
public class OrderDetailCollectorFactory implements ApplicationContextAware {

  private static Logger logger = LoggerFactory.getLogger(OrderDetailCollectorFactory.class);

  private ApplicationContext applicationContext;

  private Map<String, OrderDetailCollector> orderDetailCollectorMap;

  private static boolean hasInitialized = false;

  @PostConstruct
  public void init() {
    try {
      if(!hasInitialized){
        synchronized (OrderDetailCollectorFactory.class){
          if(!hasInitialized) {
            orderDetailCollectorMap = applicationContext.getBeansOfType(OrderDetailCollector.class);
            logger.info("detailCollectorMap: {}", orderDetailCollectorMap);
          }
        }
      }
    } catch (Exception ex) {
      logger.error("failed to load order detail collector !");
      throw new RuntimeException(ServerError.getMessage());
    }

  }

  public OrderDetailCollector get(String name) {
    return orderDetailCollectorMap.get(name);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}

