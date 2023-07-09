package shared.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;

/**
 * @author qin.shu
 * @Date 2022/9/16
 */
public class UniqueKeyUtils {

    private static final Logger LOG = LoggerFactory.getLogger(UniqueKeyUtils.class);

    /**
     * 根据指定可变参数生成用于唯一key的MD5值
     */
    public static String genUniqueKey(String... keyparts) {
        StringBuilder sb = new StringBuilder();
        for (String part : keyparts) {
            sb.append(part);
        }
        try {
            return EncryptUtils.md5(sb.toString());
        } catch (NoSuchAlgorithmException e) {
            LOG.error("genUniqueKey-md5 encrypt error {}", keyparts, e);
            return null;
        }
    }
}
