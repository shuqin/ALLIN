package zzz.study.javatech.serialization;

import java.awt.*;
import java.io.Serializable;

public class ColorPoint implements Serializable {

    private static final long serialVersionUID = 4955119713722097479L;

    private double x;
    private double y;
    private Color color;

    private transient String note = "A Point";

    public ColorPoint() {
    }

    public ColorPoint(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public ColorPoint(double x, double y) {
        this(x, y, Color.BLACK);
    }

    public String toString() {
        String colorDesc = describe(color);
        return "[" + x + ", " + y + "]: " + colorDesc;
    }

    private String describe(Color color) {
        return "(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ")"
                + " note: " + note;

    }

}
