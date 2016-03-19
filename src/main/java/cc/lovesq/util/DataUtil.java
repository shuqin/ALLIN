package cc.lovesq.util;

import java.text.DecimalFormat;

public class DataUtil {

    private static DecimalFormat df = new java.text.DecimalFormat("#.00");

    /**
     * 保留2位小数点
     */
    public static double retainNpoints(double d) {
        return Double.parseDouble(df.format(d));
    }

}
