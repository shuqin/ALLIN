package zzz.study.utils;

public class ArrayUtil {

  private ArrayUtil() {
  }

  /**
   * shift: 数组左移 将数组中除第一个元素外的所有元素前移一个位置，给定元素放入最后一个位置.
   */
  public static void shift(String[] src, String word) {
    for (int i = 0; i < src.length - 1; i++) {
      src[i] = src[i + 1];
    }
    src[src.length - 1] = word;
  }


}
