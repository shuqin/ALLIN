package zzz.study.utils;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base64编码
 * Created by qinshu on 2022/1/29
 */
public class Base64Utils {

    /**
     * base64压缩文件内容
     */
    public static String encodeContent(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        byte[] decode = Base64.getEncoder().encode(content.getBytes(StandardCharsets.UTF_8));
        return new String(decode, StandardCharsets.UTF_8);
    }

    /**
     * base64解压缩文件内容
     */
    public static String decodeContent(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        byte[] decode = Base64.getDecoder().decode(content);
        return new String(decode, StandardCharsets.UTF_8);
    }

}
