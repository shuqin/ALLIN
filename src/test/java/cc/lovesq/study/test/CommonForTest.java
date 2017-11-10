package cc.lovesq.study.test;

import org.junit.Assert;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

/**
 * Created by shuqin on 17/11/10.
 */
public class CommonForTest {

  public static final String NOT_THROW_EXCEPTION = "Not Throw Exception";

  public void eq(Object expected, Object actual) {
    assertEquals(expected, actual);
  }

  public <T> void eq(T[] expected, T[] actual) {
    Assert.assertArrayEquals(expected, actual);
  }

  public <T> void eq(List<T> expectedList, List<T> actualList) {
    if (expectedList == null && actualList == null) {
      return ;
    }
    assertEquals(expectedList.size(), actualList.size());
    for (int i=0; i< expectedList.size(); i++) {
      assertEquals(expectedList.get(i), actualList.get(i));
    }
  }

  public void fail(String message) {
    Assert.fail(message);
  }

}
