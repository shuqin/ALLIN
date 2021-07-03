package zzz.study.foundations.generics;

import zzz.study.foundations.sharingclasses.Dog;

public class TupleDemo {

    public static <T, V> Tuple<T, V> getTuple(T t, V v) {
        return new Tuple<T, V>(t, v);
    }

    public static void main(String[] args) {

        Tuple<String, Double> tuple = getTuple("PI", 3.14);
        System.out.println(tuple);
        System.out.println(tuple.getA());
        System.out.println(tuple.getB());

        //! tuple = getTuple(1, 3.14); tuple 只能持有 <String, Double> 的元组对象
        Tuple<Dog, Integer> anotherTuple = getTuple(new Dog("Gougou"), 120);
        System.out.println(anotherTuple);
        System.out.println(anotherTuple.getA());
        System.out.println(anotherTuple.getB());
    }

}
