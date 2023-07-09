package shared.utils;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shared.multitasks.customized.MyThreadPoolExecutor;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CmdOutputUtils: java 执行命令工具类
 * created by qin.shu 2023/2/9
 */
public class CmdOutputUtils {

    private static final Logger LOG = LoggerFactory.getLogger(CmdOutputUtils.class);

    private static final String FILTER_PATTERN = "\\{\".*\\}";

    private static final Pattern pattern = Pattern.compile(FILTER_PATTERN);

    private static final String CMD_THREAD_NAME = "cmd_thread";

    private static final MyThreadPoolExecutor cmdExecutor = MyThreadPoolExecutor.getInstance(3, 5, 5,
            1000, CMD_THREAD_NAME);

    public static CmdOutput exec(String cmd, long timeout) {

        try {
            Process process = new ProcessBuilder().command("sh", "-c", cmd).start();

            FutureTask ft = new FutureTask(new CmdOutTask(cmd, process));
            cmdExecutor.execute(ft);

            // 进程超时时间在引擎超时时间的基础上加5秒
            long finalTimeout = timeout + 5;
            boolean ret = process.waitFor(finalTimeout, TimeUnit.SECONDS);
            if (ret) {
                return (CmdOutput) ft.get(finalTimeout*1000, TimeUnit.MILLISECONDS);
            } else {
                LOG.info("cmd exec timeout, cmd:{}", cmd);
                process.destroyForcibly();
                return new CmdOutput(StringUtils.EMPTY, "timeout", true);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 过滤掉有用的信息。脚本执行可能存在提示信息或错误信息
     */
    public static String filterMessage(String message) {
        if (StringUtils.isEmpty(message)) {
            return StringUtils.EMPTY;
        }

        Matcher m = pattern.matcher(message);
        if (m.find()) {
            return m.group();
        }
        return StringUtils.EMPTY;
    }

    @Getter
    @Setter
    public static class CmdOutput {
        private String message;
        private String error;
        private boolean timeout;

        public CmdOutput(String message, String error, boolean timeout) {
            this.message = message;
            this.error = error;
            this.timeout = timeout;
        }
    }

    @Getter
    @Setter
    public static class CmdOutTask implements Callable<CmdOutput> {

        private String cmd;
        private Process process;

        public CmdOutTask(String cmd, Process process) {
            this.cmd = cmd;
            this.process = process;
        }

        @Override
        public CmdOutput call() {

            String info = StringUtils.EMPTY;
            String error = StringUtils.EMPTY;

            try {

                error = IOUtils.toString(process.getErrorStream(), String.valueOf(StandardCharsets.UTF_8));
                LOG.info("cmd error : {}", error);

                info = IOUtils.toString(process.getInputStream(), String.valueOf(StandardCharsets.UTF_8));
                LOG.info("cmd info : {}", info);

                info = filterMessage(info);

                return new CmdOutput(info, error, false);
            } catch (Exception ex) {
                String errorInfo = String.format("exec cmd error: cmd %s reason: %s", cmd, ex.getMessage());
                LOG.error(errorInfo, ex);
                return new CmdOutput(info, error + " reason: " + errorInfo, false);
            }
        }
    }
}
