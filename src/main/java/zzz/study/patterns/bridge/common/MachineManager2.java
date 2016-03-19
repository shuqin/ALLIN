package zzz.study.patterns.bridge.common;

public interface MachineManager2 {
	
	/** 启动机器 */
	void startMachine();
	
	/** 关闭机器 */
	void stopMachine();

	/** 开始处理 */
	void startProcess();
	
	/** 结束处理 */
	void stopProcess();
	
	/** 传入物品 */
	void conveyIn();
	
    /** 传出物品 */
	void conveyOut();
	
	/** 中止运行 */
	void shutdown();

}
