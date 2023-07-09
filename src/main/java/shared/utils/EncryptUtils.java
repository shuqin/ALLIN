package shared.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 编码相关工具类
 */
public class EncryptUtils {

    private static final String DEFAULT_AES_KEY = "1WdV2EfB#rGn$tHm";
    private static final String MD5_STR = "MD5";
    private static final String SHA_256_STR = "SHA-256";

    private transient static final String HIDE_PWD = "******";

    /**
     * SHA256 安全加密算法
     */
    public static String sha256(byte[] data) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(SHA_256_STR);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        digest.update(data);
        //获取字节数组
        byte[] messageDigest = digest.digest();
        // Create Hex String
        StringBuilder hexString = new StringBuilder();
        // 字节数组转换为 十六进制数
        for (byte aMessageDigest : messageDigest) {
            String shaHex = Integer.toHexString(aMessageDigest & 0xFF);
            if (shaHex.length() < 2) {
                hexString.append(0);
            }
            hexString.append(shaHex);
        }
        return hexString.toString();
    }

    public static String hidePwd(String pwd, int length) {
        String result = pwd;
        if (StringUtils.isNotBlank(pwd) && pwd.length() > length) {
            result = pwd.substring(0, length) + HIDE_PWD;
        }
        return result;
    }

    public static String md5(String str) throws NoSuchAlgorithmException {
        return md5(str.getBytes(UTF_8));
    }

    /**
     * 计算文件的md5值
     *
     * @param file 文件
     */
    public static String md5(File file) throws IOException, NoSuchAlgorithmException {
        return md5(file, file.length());
    }

    /**
     * 计算文件从0到end指定字节的md5值
     *
     * @param file 文件
     * @param end
     */
    public static String md5(File file, long end) throws IOException, NoSuchAlgorithmException {
        MessageDigest MD5 = MessageDigest.getInstance(MD5_STR);
        FileInputStream fileInputStream = new FileInputStream(file);
        int byteBufferLength = 8192;
        long readedRound = 0;
        int length;
        byte[] buffer;
        // 如果要读取的字节数小于等于默认buffer大小，直接创建buffer的大小为要读取数据的大小
        if (end <= byteBufferLength) {
            buffer = new byte[(int) end];
            fileInputStream.read(buffer);
            MD5.update(buffer, 0, (int) end);
        } else {
            buffer = new byte[byteBufferLength];
            while ((length = fileInputStream.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
                readedRound++;
                if (end > byteBufferLength && (readedRound + 1) * byteBufferLength > end) {
                    byte[] last = new byte[(int) (end - (readedRound * byteBufferLength))];
                    fileInputStream.read(last);
                    MD5.update(last, 0, last.length);
                    break;
                }

            }
        }
        return new String(Hex.encodeHex(MD5.digest()));
    }

    public static String md5(byte[] bytes) throws NoSuchAlgorithmException {
        String result;
        MessageDigest md = MessageDigest.getInstance(MD5_STR);
        md.update(bytes);
        byte[] b = md.digest();
        int i;
        StringBuilder buf = new StringBuilder();
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0) {
                i += 256;
            }
            if (i < 16) {
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));
        }
        result = buf.toString();
        return result;
    }

    public static String md5WithBase64(String str) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance(MD5_STR);
        return base64Encode(md5.digest(str.getBytes(UTF_8)));
    }

    public static String base64Encode(byte[] content) {
        return Base64.getEncoder().encodeToString(content);
    }

    public static byte[] base64Decode(byte[] content) {
        return Base64.getDecoder().decode(content);
    }

    public static byte[] base64Decode(String content) {
        return Base64.getDecoder().decode(content);
    }

    public static String base64DecodeToString(String content) {
        return new String(Base64.getDecoder().decode(content), UTF_8);
    }

    public static String decryptProxyPass(String proxyPass) {
        int keyLength = DEFAULT_AES_KEY.length();
        int length = proxyPass.length();
        StringBuilder sb = new StringBuilder();
        int j = 0;
        for (int i = 0; i < length; i++) {
            if (j >= keyLength) {
                j = 0;
            }
            sb.append((char) (proxyPass.charAt(i) ^ DEFAULT_AES_KEY.charAt(j++)));

        }
        return sb.toString();
    }

}
