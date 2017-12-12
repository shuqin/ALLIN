package zzz.study.function;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by shuqin on 17/12/3.
 */
public class FunctionUtil {

  public static <T,R> List<R> multiGetResult(List<Function<List<T>, R>> functions, List<T> list) {
    return functions.stream().map(f -> f.apply(list)).collect(Collectors.toList());
  }

  public static <K,R> List<R> mergeList(List<R> srcList, List<R> destList ,
                                        Function<R,K> keyFunc,
                                        BinaryOperator<R> mergeFunc) {
    return mergeList(srcList, destList, keyFunc, keyFunc, mergeFunc);
  }

  public static <T,S,K,R> List<R> mergeList(List<S> srcList, List<T> destList ,
                                          Function<S,K> skeyFunc, Function<T,K> dkeyFunc,
                                          BiFunction<S,T,R> mergeFunc) {

    Map<K,S> srcMap = srcList.stream().collect(Collectors.toMap(skeyFunc, s -> s, (k1,k2) -> k1));
    return destList.stream().map(
        dest -> {
          K key = dkeyFunc.apply(dest);
          S src = srcMap.get(key);
          return mergeFunc.apply(src, dest);
        }
    ).collect(Collectors.toList());

  }

  /** 对给定的值 x,y 应用指定的二元操作函数 */
  public static <T,S,R> Function<BiFunction<T,S,R>, R> op(T x, S y) {
    return opFunc -> opFunc.apply(x, y);
  }

  /** 将两个函数使用组合成一个函数，这个函数接受一个二元操作函数 */
  public static <T,S,Q,R> Function<BiFunction<S,Q,R>, R> op(Function<T,S> funcx, Function<T,Q> funcy, T x) {
    return opFunc -> opFunc.apply(funcx.apply(x), funcy.apply(x));
  }

  public static <T,S,Q,R> Function<BiFunction<S,Q,R>, Function<T,R>> op(Function<T,S> funcx, Function<T,Q> funcy) {
    return opFunc -> aT -> opFunc.apply(funcx.apply(aT), funcy.apply(aT));
  }

  /** 将两个函数组合成一个叠加函数, compose(f,g) = f(g) */
  public static <T> Function<T, T> compose(Function<T,T> funcx, Function<T,T> funcy) {
    return x -> funcx.apply(funcy.apply(x));
  }

  /** 将若干个函数组合成一个叠加函数, compose(f1,f2,...fn) = f1(f2(...(fn))) */
  public static <T> Function<T, T> compose(Function<T,T>... extraFuncs) {
    if (extraFuncs == null || extraFuncs.length == 0) {
      return x->x;
    }
    return x -> Arrays.stream(extraFuncs).reduce(y->y, FunctionUtil::compose).apply(x);
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

     System.out.println(op(new Integer(3), Integer.valueOf(3)).apply((x,y) -> x.equals(y.toString())));

     System.out.println(op(x-> x.length(), y-> y+",world", "hello").apply((x,y) -> x+" " +y));

     System.out.println(op(x-> x, y-> y+",world").apply((x,y) -> x+" " +y).apply("hello"));

   }

}
