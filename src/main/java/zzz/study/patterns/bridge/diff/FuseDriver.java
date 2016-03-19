package zzz.study.patterns.bridge.diff;

import zzz.study.patterns.bridge.FuseController;

public class FuseDriver extends FuseController implements MachineManager {

    /**
     * 启动机器
     */
    public void startMachine() {
        start();
    }

    /**
     * 关闭机器
     */
    public void stopMachine() {
        stop();
    }

    /**
     * 开始处理
     */
    public void startProcess() {
        startProcess();
    }

    /**
     * 结束处理
     */
    public void stopProcess() {
        stopProcess();
    }

    /**
     * 传入物品
     */
    public void conveyIn() {
        transferIn();
    }

    /**
     * 传出物品
     */
    public void conveyOut() {
        discharge();
    }

    /**
     * 中止运行
     */
    public void shutdown() {
        stopProcess();
        conveyOut();
        stopMachine();
    }


}
