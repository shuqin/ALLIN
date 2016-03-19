package zzz.study.patterns.proxy.dynamic;

import java.lang.reflect.Proxy;

public class ProxyFactory {

    /**
     * getProxy ：
     *  obtain the proxy instance of target object.
     * @param object the target object to be proxyed.
     *
     */
    public static Object getProxy(Object object) {

        ProxyHandler handler = new ProxyHandler();
        handler.setTarget(object);

        return Proxy.newProxyInstance(Dog.class.getClassLoader(),
                object.getClass().getInterfaces(), handler);
    }
}
