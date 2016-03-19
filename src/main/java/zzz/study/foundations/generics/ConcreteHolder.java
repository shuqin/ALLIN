package zzz.study.foundations.generics;

import zzz.study.foundations.typeinfo.TypeUtils;

/**
 * @Description TODO
 * @Date 2021/5/14 1:06 下午
 * @Created by qinshu
 */
public class ConcreteHolder extends Holder2<Integer> {

    public ConcreteHolder(Integer obj) {
        super(obj);
    }

    public static void main(String[] args) {
        ConcreteHolder ch = new ConcreteHolder(5);
        System.out.println(TypeUtils.getParameterizedType(ch.getClass()));

        Holder2 holder2 = new Holder2(5);
        System.out.println(TypeUtils.getParameterizedType(holder2.getClass()));
    }
}
