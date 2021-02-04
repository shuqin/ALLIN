package cc.lovesq.experiments;

import cc.lovesq.controller.RandomValueController;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Date 2021/1/28 4:39 上午
 * @Created by qinshu
 */
//@Component
public class HystrixExperiment implements IExperiment {

    @Resource
    private RandomValueController randomValueController;

    @Override
    public void test() {
        for (int i=0; i < 10000000; i++) {
            randomValueController.randInt();
        }
    }
}
