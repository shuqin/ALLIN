package zzz.study.patterns.bridge.common;

import zzz.study.patterns.bridge.StarPressController;

public class StarPressManager extends MachineManager {

    StarPressController spc = new StarPressController();

    /**
     * 启动机器
     */
    public void startMachine() {
        spc.startMachine();
    }

    /**
     * 关闭机器
     */
    public void stopMachine() {
        spc.stopMachine();
    }

    /**
     * 开始处理
     */
    public void startProcess() {
        spc.begin();
    }

    /**
     * 结束处理
     */
    public void stopProcess() {
        spc.end();
    }

    /**
     * 传入物品
     */
    public void conveyIn() {
        spc.conveyIn();
    }

    /**
     * 传出物品
     */
    public void conveyOut() {
        spc.conveyOut();
    }

    /**
     * 更换池
     */
    public void switchPool() {
        spc.switchPool();
    }

}
