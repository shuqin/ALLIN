package cc.lovesq.invoker.impl;

import java.util.Map;
import cc.lovesq.invoker.constants.Partner;

public class HttpInvokePolicy {

    private Map<String, HttpInvoke> httpInvokeMap;

    public void setHttpInvokeMap(Map<String, HttpInvoke> httpInvokeMap) {
        this.httpInvokeMap = httpInvokeMap;
    }

    public HttpInvoke getHttpInvoke(String partner) {
        if (!Partner.isDefined(partner)) {
            return null;
        }
        HttpInvoke invoke = httpInvokeMap.get(partner);
        return invoke;
    }
}
