package zzz.study.foundations.array;

public class TestUnsafePrivate {

    public static void main(String[] args) {

        UnSafePrivate usp = new UnSafePrivate();
        String[] getPrivate = usp.getUnSafePrivate();
        // 仍然可以访问和修改到类【UnSafePrivate】的私有数组变量的成员，
        // 实际上是声明的数组是伪私有成员。
        for (String s : getPrivate) {
            System.out.printf(s + " ");
        }
        System.out.println();
        getPrivate[getPrivate.length - 1] = "girl";
        for (String s : getPrivate) {
            System.out.printf(s + " ");
        }
    }

}

/*
 * Note:
 * 这说明，当数组作为类的私有成员时，不能简单地使用public get 方法进行获取整个数组，
 * 否则，相当于将数组成员声明为public的，起不到封装的作用。
 *
 * 类似的，如果一个引用作为类的私有成员时，简单地使用public get 方法获取其引用，
 * 同样是暴露了该引用所引用的对象。
 *
 */

