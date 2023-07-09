package shared.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author qin.shu
 * @Date 2022/9/22
 * @Description 执行命令工具类
 */
public class CmdRunUtils {

    private static final Logger LOG = LoggerFactory.getLogger(CmdRunUtils.class);

    public static String[] getCmdOutput(String cmd) throws InterruptedException, IOException {
        Process process = new ProcessBuilder().command("sh", "-c", cmd).start();
        boolean ret = process.waitFor(3, TimeUnit.SECONDS);
        String output = StringUtils.EMPTY;
        String error = IOUtils.toString(process.getErrorStream(), String.valueOf(StandardCharsets.UTF_8));
        if (ret) {
            output = IOUtils.toString(process.getInputStream(), String.valueOf(StandardCharsets.UTF_8));
            LOG.info("cmd: {} output: {}", cmd, output);
        } else {
            LOG.warn("cmd:{} ret is false", cmd);
            process.destroyForcibly();
        }
        return new String[] { output, error };
    }

    public static boolean isUpxUnshellSuccess(String cmdOutput) {
        // 这里可以用正则，不过正则比较耗性能，因此这里直接先写死，保证性能
        return cmdOutput.contains("Unpacked 1 file");
    }

}
