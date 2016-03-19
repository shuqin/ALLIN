package zzz.study.patterns.bridge.common;

import zzz.study.patterns.bridge.StarPressController;

public class StarPressManager2 extends StarPressController implements MachineManager2 {
	
	/** 启动机器 */
	public void startMachine() {
		startMachine();
	}
	
	/** 关闭机器 */
	public void stopMachine() {
		stopMachine();
	}

	/** 开始处理 */
	public void startProcess() {
		begin();
	}
	
	/** 结束处理 */
	public void stopProcess() {
		end();
	}
	
	/** 传入物品 */
	public void conveyIn() {
		conveyIn();
	}
	
    /** 传出物品 */
	public void conveyOut() {
		conveyOut();
	}
    
	/** 中止运行 */
	public void shutdown() {
		stopProcess();
		conveyOut();
		stopMachine();
	}
}
