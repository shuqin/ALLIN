package cc.lovesq.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

/**
 * @Description 返回随机值
 * @Date 2021/1/28 4:37 上午
 * @Created by qinshu
 */
@Controller
@RequestMapping("/rand")
public class RandomValueController {

    private static Log log = LogFactory.getLog(RandomValueController.class);

    Random rand = new Random(47);

    @HystrixCommand(commandKey = "randInt", fallbackMethod = "randIntDowngrade",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
            })
    @RequestMapping("/randInt")
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
