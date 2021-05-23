package shared.data;

import zzz.study.foundations.iolearn.RWTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @Description 测试数据生成
 * @Date 2021/5/23 4:28 下午
 * @Created by qinshu
 */
public class DataGenerator {

    public static <T> List<T> generate(int num, Function<Integer, T> func) {
        List<T> origin = new ArrayList<>();
        for (int i=0; i<num; i++) {
            origin.add(func.apply(i));
        }
        return origin;
    }

    public static List<String> readWords(String filename) {
        String text = RWTool.readFromSource(filename);
        return Arrays.asList(text.split("\\s+"));
    }
}
