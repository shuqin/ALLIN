package cc.lovesq.config;

/**
 * 组件执行方式
 * serial: 串行 parallel 并发
 */
public enum ExecWay {

    serial,
    parallel,

    ;

    public static ExecWay get(String name) {
        for (ExecWay execWay : ExecWay.values()) {
            if (execWay.name().equals(name)) {
                return execWay;
            }
        }
        return serial;
    }
}
