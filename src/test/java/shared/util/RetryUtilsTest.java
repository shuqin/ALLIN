package shared.util;

import cc.lovesq.exception.BizException;
import org.junit.Test;
import shared.utils.RetryUtils;

/**
 * RetryUtilsTest:
 * created by qin.shu 2023/7/9
 */
public class RetryUtilsTest {

    @Test
    public void testRetry() {
        int[] i = new int[1];
        RetryUtils.call(() -> {
            System.out.println(i[0]);
            i[0] += 1;
            throw new RuntimeException("Exception");
        }, 3, 1);
    }
}
