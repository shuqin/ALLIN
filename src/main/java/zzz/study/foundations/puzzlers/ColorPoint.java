package zzz.study.foundations.puzzlers;

class Point {
    protected final int x, y;
    private final String name; // Cached at construction time

    Point(int x, int y) {
        this.x = x;
        this.y = y;
        name = makeName();
    }

    protected String makeName() {
        return "[" + x + "," + y + "]";
    }

    public final String toString() {
        return name;
    }
}

public class ColorPoint extends Point {
    private final String color;

    ColorPoint(int x, int y, String color) {
        super(x, y);
        this.color = color;
    }

    public static void main(String[] args) {
        System.out.println(new ColorPoint(4, 2, "purple"));
    }

    protected String makeName() {
        return super.makeName() + ":" + color;
    }
}

/*
 * Note:
 *
 * 不要在构造器中调用被覆写的方法，否则，可能导致实例初始化错误。
 *
 */
