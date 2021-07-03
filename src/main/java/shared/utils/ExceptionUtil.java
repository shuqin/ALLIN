package shared.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by shuqin on 18/3/13.
 */
public class ExceptionUtil {

    private static Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);

    public static <T, R> R doWithRobust(Function<T, R> producer, T param) {
        try {
            return producer.apply(param);
        } catch (Exception ex) {
            logger.error("failed to execute consumer {} {} ", producer.getClass().getName(), param);
            return null;
        }
    }

    public static <T> void doWithRobust(Consumer<T> consumer, T param) {
        try {
            consumer.accept(param);
        } catch (Exception ex) {
            logger.error("failed to execute consumer {} {} ", consumer.getClass().getName(), param);
        }
    }

    public static <T, R> void doWithRobust(BiConsumer<T, R> consumer, T t, R r) {
        try {
            consumer.accept(t, r);
        } catch (Exception ex) {
            logger.error("failed to execute consumer" + consumer.getClass().getName() + " " + t + " " + r,
                    ex);
        }
    }

}
