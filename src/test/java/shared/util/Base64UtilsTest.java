package shared.util;

import org.junit.Test;
import shared.utils.Base64Utils;

/**
 * Base64UtilsTest:
 * created by qin.shu 2023/7/9
 */
public class Base64UtilsTest {

    @Test
    public void test() {
        String hello = "hello world!";
        String encoded = Base64Utils.encodeContent(hello);
        String origin = Base64Utils.decodeContent(encoded);
        System.out.println(origin);
    }
}
