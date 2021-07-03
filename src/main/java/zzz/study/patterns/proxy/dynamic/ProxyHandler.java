package zzz.study.patterns.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 在拦截 Dog 实例时自动调用 DogInterceper 的方法
 */
public class ProxyHandler implements InvocationHandler {

    /**
     * 创建拦截器实例
     */
    DogIntercepter dip = new DogIntercepter();
    /**
     * 被代理的目标对象
     */
    private Object target;

    /**
     * 执行代理的目标方法时，该方法被自动调用
     */
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Exception {
        Object result = null;
        if (method.getName().equals("info")) {
            System.out.println(" *** execute info(): *** ");
            dip.methodOne();
            result = method.invoke(target, args);
            dip.methodTwo();
        } else if (method.getName().equals("run")) {
            System.out.println(" *** execute run(): *** ");
            dip.methodThree();
            result = method.invoke(target, args);
        } else {
            result = method.invoke(target, args);
        }
        doOthers();
        return result;
    }

    public void doOthers() {

        System.out.println("do other things");
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}
