package zzz.study.foundations.inface;

import zzz.study.foundations.inface.inner.InterfaceWithConstant;

public class ReadingConstantsInInterface {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(InterfaceWithConstant.PI);
            System.out.println(InterfaceWithConstant.VARIABLE);
        }
    }

}

/*
 * 接口中的常量默认是 public static final 的.
 */