package shared.conf;

import lombok.Getter;

@Getter
public class GlobalConfig {

    // groovyScript缓存池的Script对象最大缓存数量
    private int cacheMaxTotal = 50;

    // 获取缓存Script对象时的最大等待时间(ms)
    private int maxWaitMillis = 1000;

}
