package zzz.study.foundations.innerclass.authority;

public class AccessAuthority {

    private boolean authority;

    public static void main(String[] args) {

        Relative.main(args);

        System.out.println(" ************ Enter into the main: " + AccessAuthority.class.getName() + "*************");

        // 可以在主类中直接创建和使用其辅助类的对象、其【私有、默认、公有内部类】的对象。
        System.out.println(new Relative(5));
        AccessAuthority access = new AccessAuthority();

        System.out.println(access.new PublicInnerclass(1));
        System.out.println(access.new Innerclass(2));
        System.out.println(access.new PrivateInnerclass(3));

        // 可以在主类中间接创建和使用其辅助类的对象、其【私有、默认、公有内部类】的对象。
        System.out.println(" -------- 间接创建 ------------- ");
        System.out.println(access.getRelative());
        System.out.println(access.getInner());
        System.out.println(access.getPrivateInner());
        System.out.println(access.getPublicInner());

        System.out.println();

    }

    public Innerclass getInner() {
        return new Innerclass();
    }

    public PrivateInnerclass getPrivateInner() {
        return new PrivateInnerclass();
    }

    public PublicInnerclass getPublicInner() {
        return new PublicInnerclass();
    }

    public Relative getRelative() {
        return new Relative();
    }

    class Innerclass {
        private int i;

        Innerclass(int i) {
            this.i = i;
        }

        Innerclass() {
            this.i = 0;
        }

        public String toString() {
            return getClass().getName() + "[" + i + "]: " + authority;
        }

    }

    private class PrivateInnerclass {
        private int i;

        PrivateInnerclass(int i) {
            this.i = i;
        }

        PrivateInnerclass() {
            this.i = 0;
        }

        public String toString() {
            return getClass().getName() + "[" + i + "]: " + authority;
        }
    }

    public class PublicInnerclass {
        private int i;

        public PublicInnerclass(int i) {
            this.i = i;
        }

        public PublicInnerclass() {
            this.i = 0;
        }

        public String toString() {
            return getClass().getName() + "[" + i + "]: " + authority;
        }

    }


}

class Relative {
    private int j;

    Relative(int j) {
        this.j = j;
    }

    Relative() {
        this.j = 0;
    }

    public static void main(String[] args) {

        System.out.println(" ************ Enter into the main: " + Relative.class.getName() + "*************");

        // 可以在主类的辅助类中自由创建其主类的【默认、公有内部类】的对象。
        System.out.println(new Relative(5));

        AccessAuthority access = new AccessAuthority();

        System.out.println(access.new PublicInnerclass(1));
        System.out.println(access.new Innerclass(2));
        //!Error 在辅助类中无法创建其主类的私有内部类的对象。
        //System.out.println(access.new PrivateInnerclass(3));

        // 可以间接创建主类的【私有、默认、公有内部类】的对象。
        System.out.println(" -------- 间接创建 ------------- ");
        System.out.println(access.getRelative());
        System.out.println(access.getInner());
        System.out.println(access.getPrivateInner());
        System.out.println(access.getPublicInner());

        System.out.println();
    }

    public void get() {
        AccessAuthority access = new AccessAuthority();
        //! Error 无法访问 AccessAuthority 的私有成员。 
        // access.authority;
    }

    public String toString() {
        return getClass().getName() + "[" + j + "]";
    }


}


