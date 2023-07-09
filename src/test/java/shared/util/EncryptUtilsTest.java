package shared.util;

import org.junit.Test;
import shared.utils.EncryptUtils;
import zzz.study.foundations.iolearn.RWTool;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * EncryptUtilsTest:
 * created by qin.shu 2023/7/9
 */
public class EncryptUtilsTest {

    @Test
    public void test() throws IOException, NoSuchAlgorithmException {
        System.out.println(EncryptUtils.md5(new File("/Users/qinshu/videolinks.txt")));
        System.out.println(EncryptUtils.sha256(RWTool.readBytes("/Users/qinshu/setup/upx-4.0.1-i386_linux/upx")));

    }
}
