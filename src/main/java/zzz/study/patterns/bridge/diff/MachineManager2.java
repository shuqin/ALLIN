package zzz.study.patterns.bridge.diff;

public abstract class MachineManager2 {

    private MachineManager driver;  // 采用委托的方式

    public void setTimeout(double time) {
        // 默认实现
    }

}
