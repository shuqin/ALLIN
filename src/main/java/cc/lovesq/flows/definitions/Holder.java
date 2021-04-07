package cc.lovesq.flows.definitions;

/**
 * @Description TODO
 * @Date 2021/4/7 9:25 上午
 * @Created by qinshu
 */
public class Holder<T> {

    T data;

    public Holder(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
