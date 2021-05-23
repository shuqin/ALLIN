package shared.multitasks;

import cc.lovesq.CommonForTest;
import cc.lovesq.model.Word;
import org.junit.Test;
import shared.data.DataGenerator;
import shared.multitasks.customized.MyThreadPoolExecutor;

import java.util.List;

/**
 * @Description TODO
 * @Date 2021/5/23 4:26 下午
 * @Created by qinshu
 */
public class MyThreadPoolExecutorTest extends CommonForTest {

    @Test
    public void testGetMultiTaskResult() {
        List<String> origin = DataGenerator.readWords("/eventflows.yml");
        List<Word> words = MyThreadPoolExecutor.getInstance("default").getMultiTaskResult(
                origin, s -> new Word(s, s.length())
        );
        eq(words.size(), origin.size());
        System.out.println(words);
    }
}
