package shared.performance;

import java.util.function.Consumer;

/**
 * 消费器包装
 * Created by qinshu on 2021/7/11
 */
public class ConsumerWrapper<T> {

    private Consumer<T> consumer;
    private T param;

    public ConsumerWrapper(Consumer<T> consumer, T param) {
        this.consumer = consumer;
        this.param = param;
    }

    public void run() {
        consumer.accept(param);
    }
}
