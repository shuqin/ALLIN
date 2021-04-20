package cc.lovesq;

import org.junit.Assert;

import java.util.List;

/**
 * @Description TODO
 * @Date 2021/4/16 11:20 上午
 * @Created by qinshu
 */
public class CommonForAssert {

    public void eq(Object expected, Object actual) {
        Assert.assertEquals(expected, actual);
    }

    public void eq(long expected, long actual) {
        Assert.assertEquals(expected, actual);
    }

    public void eq(List<Object> expected, List<Object> actual) {
        eq(expected.size(), actual.size());
        for (int i=0; i< expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }
    }

    public void fail(String message) {
        Assert.fail(message);
    }

}
