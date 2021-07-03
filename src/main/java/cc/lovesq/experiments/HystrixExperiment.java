package cc.lovesq.experiments;

import cc.lovesq.service.impl.RandomValueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Date 2021/1/28 4:39 上午
 * @Created by qinshu
 */
//@Component
public class HystrixExperiment implements IExperiment {

    private static Logger logger = LoggerFactory.getLogger(HystrixExperiment.class);

    @Resource
    private RandomValueService randomValueService;

    @Override
    public void test() {
        logger.info("----HystrixExperiment-start----");
        for (int i = 0; i < 100000; i++) {
            try {
                randomValueService.randInt();
            } catch (Exception ex) {
                logger.error("CatchedException: " + ex.getMessage(), ex);
            }

        }
        logger.info("----HystrixExperiment-end----");
    }
}
