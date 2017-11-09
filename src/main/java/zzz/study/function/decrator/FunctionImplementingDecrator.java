package zzz.study.function.decrator;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import static java.lang.Math.*;

/**
 * Created by shuqin on 17/6/29.
 */
public class FunctionImplementingDecrator {

  public static void main(String[] args) {

    // 求解 (sinx)^2 + (cosx)^2 = 1 的若干方法

    double x= 30;
    System.out.println(Math.pow(sin(x),2) + Math.pow(cos(x), 2));

    System.out.println(pow(Math::sin, 2).apply(x) + pow(Math::cos, 2).apply(x));

    double res = op(pow(Math::sin, 2).apply(x), pow(Math::cos, 2).apply(x)).apply((a,b) -> a+b);
    System.out.println(res);

    double res2 = op(pow(Math::sin, 2), pow(Math::cos, 2), x).apply((a,b) -> a+b);
    System.out.println(res2);

    Function<Double,Double> sumSquare = op(pow(Math::sin, 2), pow(Math::cos, 2)).apply((a,b)->a+b);
    System.out.println(sumSquare.apply(x));

    Function<Double,Double> another = op(compose((d)->d*d, Math::sin), compose((d)->d*d, Math::cos)).apply((a,b)->a+b);
    System.out.println(another.apply(x));

    Function<Double,Double> third = compose(d->d*d, d->d+1, d->d*2, d->d*d*d);  // (2x^3+1)^2
    System.out.println(third.apply(3d));
  }

  /** 将指定函数的值封装幂次函数 pow(f, n) = (f(x))^n */
  public static <T> Function<T, Double> pow(final Function<T,Double> func, final int n) {
    return x -> Math.pow(func.apply(x), (double)n);
  }

  /** 对给定的值 x,y 应用指定的二元操作函数 */
  public static <T> Function<BiFunction<T,T,T>, T> op(T x, T y) {
    return opFunc -> opFunc.apply(x, y);
  }

  /** 将两个函数使用组合成一个函数，这个函数接受一个二元操作函数(eg +, -, * , /) */
  public static <T> Function<BiFunction<T,T,T>, T> op(Function<T,T> funcx, Function<T,T> funcy, T x) {
    return opFunc -> opFunc.apply(funcx.apply(x), funcy.apply(x));
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
    return x -> Arrays.stream(extraFuncs).reduce(y->y, FunctionImplementingDecrator::compose).apply(x);
  }

  public static <T> Function<BiFunction<T,T,T>, Function<T,T>> op(Function<T,T> funcx, Function<T,T> funcy) {
    //return opFunc -> { return aT -> opFunc.apply(funcx.apply(aT), funcy.apply(aT)); };
    return opFunc -> aT -> opFunc.apply(funcx.apply(aT), funcy.apply(aT));

/*   Equivalent to
    return new Function<BiFunction<T, T, T>, Function<T, T>>() {
      @Override
      public Function<T, T> apply(
          BiFunction<T, T, T> opFunc) {

        return new Function<T, T>() {
          @Override
          public T apply(T aT) {
            return opFunc.apply(funcx.apply(aT), funcy.apply(aT));
          }
        };
      }
    };*/
  }

}
