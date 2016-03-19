package cc.lovesq.goodssnapshot.impl4;

public interface ServiceTplListInf {

    /**
     * 是否包含指定的服务 key
     */
    boolean containsKey(String key);

    /**
     * 根据指定的服务 key 及下单时间获取服务快照
     */
    ServiceTpl getTpl(String key, long timestamp);
}
