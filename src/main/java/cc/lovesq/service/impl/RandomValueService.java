package cc.lovesq.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @Description 返回随机值
 * @Date 2021/1/28 4:37 上午
 * @Created by qinshu
 */
@Component
public class RandomValueService {

    private static Log log = LogFactory.getLog(RandomValueService.class);

    Random rand = new Random(47);

    @HystrixCommand(commandKey = "randInt", fallbackMethod = "randIntDowngrade",
            commandProperties = {
                    @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value="5000"),
                    @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),
                    @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50")
            })
    public Integer randInt() {

        int v = rand.nextInt(100);

        if (v == 0) {
            throw new RuntimeException("Invalid number");
        }

        return v;

    }

    public Integer randIntDowngrade() {
        log.info("randIntDowngrade");
        return 0;
    }
}
