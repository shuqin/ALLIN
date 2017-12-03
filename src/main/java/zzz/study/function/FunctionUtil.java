package zzz.study.function;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
   }

}
