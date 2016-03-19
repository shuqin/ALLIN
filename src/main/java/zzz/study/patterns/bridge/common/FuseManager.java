package zzz.study.patterns.bridge.common;

import zzz.study.patterns.bridge.FuseController;

public class FuseManager extends MachineManager {
	
	FuseController fc = new FuseController();
	
	/** 启动机器 */
	public void startMachine() {
		fc.start();
	}
	
	/** 关闭机器 */
	public void stopMachine() {
		fc.stop();
	}

	/** 开始处理 */
	public void startProcess() {
		fc.startProcess();
	}
	
	/** 结束处理 */
	public void stopProcess() {
		fc.stopProcess();
	}
	
	/** 传入物品 */
	public void conveyIn() {
		fc.transferIn();
	}
	
    /** 传出物品 */
	public void conveyOut() {
		fc.discharge();
	}

}
