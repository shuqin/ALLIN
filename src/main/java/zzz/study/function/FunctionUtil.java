package zzz.study.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by shuqin on 17/12/3.
 */
public class FunctionUtil {

   public static <T,R> List<R> multiGetResult(List<Function<List<T>, R>> functions, List<T> list) {
     return functions.stream().map(f -> f.apply(list)).collect(Collectors.toList());
   }

   public static void main(String[] args) {
     System.out.println(multiGetResult(
         Arrays.asList(
             list -> list.stream().collect(Collectors.summarizingInt(x->x)),
             list -> list.stream().filter(x -> x < 50).sorted().collect(Collectors.toList()),
             list -> list.stream().collect(Collectors.groupingBy(x->(x%2==0? "even": "odd"))),
             list -> list.stream().sorted().collect(Collectors.toList()),
             list -> list.stream().sorted().map(Math::sqrt).collect(Collectors.toMap(x->x, y->Math.pow(2,y)))),
         Arrays.asList(64,49,25,16,9,4,1,81,36)));

     List<Integer> list = Arrays.asList(1,2,3,4,5);
     Supplier<Map<Integer,Integer>> mapSupplier = () -> list.stream().collect(Collectors.toMap(x->x, y-> y * y));

     Map<Integer, Integer> mapValueAdd = list.stream().collect(Collectors.toMap(x->x, y->y, (v1,v2) -> v1+v2, mapSupplier));
     System.out.println(mapValueAdd);

     List<Integer> nums = Arrays.asList(Arrays.asList(1,2,3), Arrays.asList(1,4,9), Arrays.asList(1,8,27))
                                .stream().flatMap(x -> x.stream()).collect(Collectors.toList());
     System.out.println(nums);

     List<Integer> fibo = Arrays.asList(1,2,3,4,5,6,7,8,9,10).stream().collect(new FiboCollector());
     System.out.println(fibo);
   }

}
