package zzz.study.function.perspective;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class PipeLine {

    public static void main(String[] args) {
        List<String> result = pipe(PipeLine::supplier, Arrays.asList(PipeLine::sorter, PipeLine::uniq), PipeLine::format);
        System.out.println(result);
    }

    public static <T, R> R pipe(Supplier<List<T>> supplier, List<Function<List<T>, List<T>>> filters,
                                Function<List<T>, R> format) {
        return format.apply(Loop.reduce(filters, (f, mid) -> f.apply(mid), supplier));
    }


    public static List<String> supplier() {
        return Arrays.asList("E20191219221321025200001", "E20181219165942035900001", "E20181219165942035900001", "E20191119165942035900001");
    }

    public static List<String> sorter(List<String> list) {
        Collections.sort(list);
        return list;
    }

    public static List<String> uniq(List<String> list) {
        return list.stream().distinct().collect(Collectors.toList());
    }

    public static List<String> format(List<String> list) {
        return list.stream().map(
                (s) -> s + " " + s.substring(1, 5) + " " + s.substring(5, 7) + ":" + s.substring(7, 9) + ":" + s.substring(9, 11)
        ).collect(Collectors.toList());
    }
}
