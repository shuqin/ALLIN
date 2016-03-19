package zzz.study.javatech.serialization;

import java.io.Serializable;

public class Point implements Serializable {

    // 没有添加 serialVersionUID

    private int x;
    private int y;  // 在对象序列化后添加 字段 y ， 模拟软件版本升级

    public Point() {
        this.x = 0;
    }

    public Point(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public String toString() {
//		 return "" + x;
        return "(" + x + ", " + y + ")"; // 添加字段 y 后
    }

}
