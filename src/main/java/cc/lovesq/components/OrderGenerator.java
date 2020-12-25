package cc.lovesq.components;

import cc.lovesq.util.DateUtil;

import java.util.Date;
import java.util.Random;

public class OrderGenerator {

    static Random random = new Random(47);

    public static String generateOrder(Date date, Long userId) {
        String prefix = "E";
        String time = DateUtil.formatSpecial(date);

        String randomNum = zeroLeader(5, random.nextInt(10000));
        return prefix + time + "0" + randomNum;
    }

    private static String zeroLeader(int n, int num) {
        StringBuilder sb = new StringBuilder();
        String numStr = String.valueOf(num);
        int zeros = n - numStr.length();
        while( zeros > 0) {
            sb.append('0');
            zeros--;
        }
        return sb.append(numStr).toString();
    }
}
