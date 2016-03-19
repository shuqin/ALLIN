package zzz.study.foundations.innerclass;

public class AccessInnerMember {

    private int i;

    private Inner inner;

    private String name;

    public AccessInnerMember(int i) {
        this.i = i;
    }

    public static void main(String[] args) {
        AccessInnerMember aim = new AccessInnerMember(1);
        aim.initInner("inner name");
        System.out.println(aim.accessInnerMember());
        System.out.println(aim.accessInnerMember2());
    }

    public void initInner(String name) {
        inner = new Inner(name);
    }

    public String accessInnerMember() { // 外部类能够直接访问内部类私有成员，必须有内部类对象的引用
        return inner.name;
    }

    public String accessInnerMember2() { // 访问的是外部类中同名的name私有成员，而不是内部类中的name私有成员
        return name;
    }

    private class Inner {
        private String name;

        public Inner(String name) {
            this.name = name;
        }
    }

}
