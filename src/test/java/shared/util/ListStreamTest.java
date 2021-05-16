package shared.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static cc.lovesq.util.ListStream.stream;

/**
 * @Description TODO
 * @Date 2021/5/16 8:05 上午
 * @Created by qinshu
 */
public class ListStreamTest {

    @Test
    public void testNull() {
        List<Integer> ints = stream(null).map(x -> (int)x + 1);
        Assert.assertEquals(ints.size(), 0);
    }

    @Test
    public void testMap() {
        List<Integer> ints = stream(Arrays.asList(1,2,3,4,5)).map(x -> x*2);
        Assert.assertArrayEquals(ints.toArray(new Integer[0]), new Integer[]{2,4,6,8,10});
    }

    @Test
    public void testFilter() {
        List<Integer> ints = stream(Arrays.asList(1,2,3,4,5)).filter(x -> x%2 ==0);
        Assert.assertArrayEquals(ints.toArray(new Integer[0]), new Integer[]{2,4});
    }

    @Test
    public void testFilterAndMapChain() {
        List<Integer> ints = stream(Arrays.asList(1,2,3,4,5)).filterAndMapChain(
                Arrays.asList(x -> x%2==1),
                x -> x*2,
                x -> x > 8);
        Assert.assertArrayEquals(ints.toArray(new Integer[0]), new Integer[]{10});
    }
}
