package zzz.study.patterns.bridge.common;

public abstract class MachineManager {
	
	/** 启动机器 */
	public abstract void startMachine();
	
	/** 关闭机器 */
	public abstract void stopMachine();

	/** 开始处理 */
	public abstract void startProcess();
	
	/** 结束处理 */
	public abstract void stopProcess();
	
	/** 传入物品 */
	public abstract void conveyIn();
	
    /** 传出物品 */
	public abstract void conveyOut();
	
	/** 中止运行 */
	public void shutdown() {
		stopProcess();
		conveyOut();
		stopMachine();
	}

}
