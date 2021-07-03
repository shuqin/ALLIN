package zzz.study.foundations.puzzlers;

public class JavaGeneric {

    public static void main(String[] args) {
        new JavaGeneric().ss();
        new JavaGeneric().test2(new C(12));
        new JavaGeneric().test2(new C<Integer>(12));
        new JavaGeneric().test2(new C<String>("qin"));
    }

    public void test(A<String, Integer> a) {
        System.out.println(a.toString());
    }

    /*
    public void test2(C<String> c) {
        System.out.println(c.toString());
    }
     */

    public <String> void test2(C<String> c) {
        System.out.println(c.toString());
    }

    public void ss() {
        test(new B(12, "qin"));
        System.out.println("success");
    }

    /*
    private void test(B<Integer> b) {

    }
    */
}

class A<S, I> {
    S name;
    I age;

    public String toString() {
        return name.getClass().getName() + " " + age.getClass().getName();
    }
}

class B<R> extends A<Integer, String> {

    public B(Integer name, String i) {
        this.name = name;
        this.age = i;
    }

}

class C<T> {
    T t;

    public C() {
    }

    public C(T t) {
        this.t = t;
    }

    public String toString() {
        return t.getClass().getName();
    }
}
