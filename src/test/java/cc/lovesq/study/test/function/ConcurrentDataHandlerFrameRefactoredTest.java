package cc.lovesq.study.test.function;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import zzz.study.function.refactor.result.ConcurrentDataHandlerFrameRefactored;
import zzz.study.function.refactor.ForeachUtil;
import zzz.study.function.refactor.StreamUtil;

/**
 * Created by shuqin on 17/6/24.
 */
public class ConcurrentDataHandlerFrameRefactoredTest {

  @Test
  public void testGetAllData() {
    List<String> allKeys = getKeys();
    List<Integer> allData = ConcurrentDataHandlerFrameRefactored.getAllData(
        allKeys, (keys) -> StreamUtil.map(keys, key -> Integer.valueOf(key) % 1000000000));  // lambda , ut mock 神器
    allKeys.forEach((key) -> allData.contains(Integer.valueOf(key)));
  }

  private static List<String> getKeys() {
    return ForeachUtil.foreachAddWithReturn(2000, (ind -> Arrays.asList(String.valueOf(ind))));
  }

}
