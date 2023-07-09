package shared.util;

import org.junit.Test;
import shared.utils.EncodingDetector;
import zzz.study.foundations.iolearn.RWTool;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * EncodingDetectorTest:
 * created by qin.shu 2023/7/9
 */
public class EncodingDetectorTest {

    @Test
    public void test() throws IOException {
        byte[] bytes = RWTool.readBytes("/Users/qinshu/setup/upx-4.0.1-i386_linux/upx");
        Charset charset = EncodingDetector.getEncoding(bytes);
        System.out.println(charset.toString());
    }
}
