package shared.util;

import org.junit.Test;
import shared.utils.CmdRunUtils;

import java.io.IOException;

/**
 * @author qin.shu
 * @Date 2022/9/22
 * @Description
 */
public class CmdRunUtilsTest {

    @Test
    public void testLsSuccess() throws IOException, InterruptedException {
        String[] cmdOutput = CmdRunUtils.getCmdOutput("ls ~");
        System.out.println(cmdOutput[0]);
    }
}
