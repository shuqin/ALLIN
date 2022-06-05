package cc.lovesq.study.test.basic;

import org.junit.Test;

import java.io.File;

/**
 * TODO
 * Created by qinshu on 2021/11/9
 */
public class FileNormalizeTest {

    @Test
    public void testFileNormalize() {
        File file = new File("/test.jsp/");
        System.out.println(file.getPath());

        File file2 = new File("test.jsp/");
        System.out.println(file2.getPath());
    }
}
