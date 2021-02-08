package cc.lovesq.experiments;

import cc.lovesq.service.impl.RandomValueService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Date 2021/1/28 4:39 上午
 * @Created by qinshu
 */
@Component
public class HystrixExperiment implements IExperiment {

    private static Log log = LogFactory.getLog(HystrixExperiment.class);

    @Resource
    private RandomValueService randomValueService;

    @Override
    public void test() {
        log.info("----HystrixExperiment-start----");
        for (int i=0; i < 100000; i++) {
            try {
                randomValueService.randInt();
            } catch (Exception ex) {
                log.error("CatchedException: " + ex.getMessage());
            }

        }
        log.info("----HystrixExperiment-end----");
    }
}
