package zzz.study.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Created by shuqin on 17/12/5.
 */
public class FiboCollector implements Collector<Integer, List<Integer>, List<Integer>> {

    public Supplier<List<Integer>> supplier() {
        return () -> {
            List<Integer> result = new ArrayList<>();
            result.add(0);
            result.add(1);
            return result;
        };
    }

    @Override
    public BiConsumer<List<Integer>, Integer> accumulator() {
        return (res, num) -> {
            Integer next = res.get(res.size() - 1) + res.get(res.size() - 2);
            res.add(next);
        };
    }

    @Override
    public BinaryOperator<List<Integer>> combiner() {
        return null;
        //return (left, right) -> { left.addAll(right); return left; };
    }

    @Override
    public Function<List<Integer>, List<Integer>> finisher() {
        return res -> {
            res.remove(0);
            res.remove(1);
            return res;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }

}
