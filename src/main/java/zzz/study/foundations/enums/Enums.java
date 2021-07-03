package zzz.study.foundations.enums;

import java.util.Random;

/***
 *
 *  枚举实用类
 *
 */

public class Enums {

    private static Random rand = new Random(47);

    public static <T extends Enum<T>> T random(Class<T> ec) {
        return random(ec.getEnumConstants());
    }

    public static <T> T random(T[] values) {
        return values[rand.nextInt(values.length)];
    }
}
