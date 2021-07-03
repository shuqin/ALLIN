package cc.lovesq.flinkdemo;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @Description 适配
 * @Date 2021/5/7 5:22 下午
 * @Created by qinshu
 */
@FunctionalInterface
public interface SerializableFunction<T, R> extends Function<T, R>, Serializable {
}
