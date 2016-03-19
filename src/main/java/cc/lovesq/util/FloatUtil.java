package cc.lovesq.util;

import java.text.DecimalFormat;

/**
 * Created by lovesqcc on 15-10-11.
 */
public class FloatUtil {

    private FloatUtil(){}

    private static DecimalFormat df   =new   java.text.DecimalFormat("#.00");

    /**
     * 保留2位小数点
     * @param d
     */
    public static double retainNpoints(double d) {
        return Double.parseDouble(df.format(d));
    }
}
