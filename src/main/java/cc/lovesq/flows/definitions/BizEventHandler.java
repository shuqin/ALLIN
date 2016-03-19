package cc.lovesq.flows.definitions;

import cc.lovesq.flows.detect.bizevents.BizEvent;

/**
 * @Description TODO
 * @Date 2021/4/7 9:29 下午
 * @Created by qinshu
 */
public interface BizEventHandler {

    public void handle(BizEvent bizEvent);
}
