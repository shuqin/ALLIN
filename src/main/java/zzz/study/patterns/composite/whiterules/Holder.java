package zzz.study.patterns.composite.whiterules;

/**
 * @Description 持值对象
 * @Date 2021/5/19 9:45 上午
 * @Created by qinshu
 */
public class Holder<T> {

    private T obj;

    public Holder(T obj) {
        this.obj = obj;
    }

    public T get() {
        return obj;
    }
}
