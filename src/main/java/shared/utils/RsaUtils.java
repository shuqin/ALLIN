package shared.utils;

import com.google.common.collect.Lists;
import com.google.common.primitives.Bytes;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RsaUtils {
    private static final Logger logger = LoggerFactory.getLogger(RsaUtils.class);
    private static final String ALGORITHM = "RSA";
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final int KEY_SIZE = 1024;
    // java rsa加密不能超过117个字节，角标从0开始就是116
    private static final int MAX_BYTES_LENGTH = 116;

    /**
     * 生成秘钥对
     */
    private static RsaKeyPair generateKeyPair() {
        try {
            KeyPairGenerator keygen = KeyPairGenerator.getInstance(ALGORITHM);
            SecureRandom random = new SecureRandom();
            keygen.initialize(KEY_SIZE, random);
            KeyPair keyPair = keygen.generateKeyPair();
            RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
            RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
            String privateKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            return new RsaKeyPair(publicKeyStr,privateKeyStr);
        } catch (Exception e) {
            throw new RuntimeException("rsa generateKeyPair error");
        }
    }

    /**
     * 获取公钥
     */
    public static RSAPublicKey getPublicKey(String publicKey) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            return (RSAPublicKey) keyFactory.generatePublic(spec);
        } catch (Exception e) {
            throw new RuntimeException("rsa getPublicKey error");
        }
    }

    /**
     * 获取私钥
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(privateKey);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            return (RSAPrivateKey) keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            throw new RuntimeException("rsa getPrivateKey error");
        }
    }

    /**
     * 加密
     */
    public static String encrypt(String content, Key pubOrPriKey) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, pubOrPriKey);
            byte[] bytes = content.getBytes(DEFAULT_CHARSET);
            List<Byte> byteList = Lists.newArrayList();
            if (bytes.length > MAX_BYTES_LENGTH) {
                byte[] splite = null;
                int i = 0;
                do {
                    splite = ArrayUtils.subarray(bytes, i, i + MAX_BYTES_LENGTH);
                    i = i + MAX_BYTES_LENGTH;
                    if (splite.length > 0) {
                        byte[] result = cipher.doFinal(splite);
                        Bytes.asList(result);
                        byteList.addAll(Bytes.asList(result));
                    }

                } while(splite != null && splite.length > 0);
            } else {
                byte[] result = cipher.doFinal(bytes);
                byteList.addAll(Bytes.asList(result));
            }
            return Base64.getEncoder().encodeToString(Bytes.toArray(byteList));
        } catch (Exception e) {
            logger.error("rsa encrypt error", e);
            throw new RuntimeException("rsa encrypt error");
        }
    }

    /**
     * 解密
     */
    public static String decrypt(String content, Key pubOrPriKey) {
        try {
            byte[] result = decryptToByte(content, pubOrPriKey);
            return new String(result, DEFAULT_CHARSET);
        } catch (Exception e) {
            logger.error("rsa decrypt error", e);
            throw new RuntimeException("rsa decrypt error");
        }
    }

    public static String decrypt(byte[] content, Key pubOrPriKey) {
        try {
            byte[] result = decryptToByte(content, pubOrPriKey);
            return new String(result, DEFAULT_CHARSET);
        } catch (Exception e) {
            logger.error("rsa decrypt error", e);
            throw new RuntimeException("rsa decrypt error");
        }
    }

    public static byte[] decryptToByte(String content, Key pubOrPriKey) {
        return decryptToByte(Base64.getDecoder().decode(content), pubOrPriKey);
    }

    public static byte[] decryptToByte(byte[] content, Key pubOrPriKey) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, pubOrPriKey);
            return cipher.doFinal(content);
        } catch (Exception e) {
            logger.error("rsa decrypt error", e);
            throw new RuntimeException("rsa decrypt error");
        }
    }

    /**
     * 针对加密逻辑是先截取成少于128字节的数据，分段加密后在拼接起来
     * 128字节的数据进行base64后的数据是172字节，php是先把数据按照128截取，然后私钥加密，然后base64，然后拼接起来
     * @param content
     * @param pubOrPriKey
     * @return
     */
    public static String decryptForPhp(String content, Key pubOrPriKey) {
        try {
            StringBuilder sb = new StringBuilder();
            if (content.length() > 172) {
                int offset = 0;
                int length = 0;
                while (length < content.length()) {
                    String sub = content.substring(offset * 172, (offset + 1) * 172);
                    sb.append(decrypt(sub, pubOrPriKey));
                    length += sub.length();
                    offset++;
                }
            } else {
                sb.append(decrypt(content, pubOrPriKey));
            }

            return sb.toString();
        } catch (Exception e) {
            logger.error("rsa decrypt error", e);
            throw new RuntimeException("rsa decrypt error");
        }
    }

    /**
     * 先将content base64解码，然后按照128为一段截取，然后对每一段使用rsa解密得到字节数组，把这些字节数组append起来，再toString
     * @param content
     * @param pubOrPriKey
     * @return
     */
    public static String decryptForJava(String content, Key pubOrPriKey) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, pubOrPriKey);
            List<Byte> byteList = Lists.newArrayList();
            byte[] datas = Base64.getDecoder().decode(content);
            if (datas.length > 128) {
                int length = 0;
                while (length < datas.length) {
                    byte[] bytes = ArrayUtils.subarray(datas, length, length + 128);
                    byte[] result = cipher.doFinal(bytes);
                    byteList.addAll(Bytes.asList(result));
                    length += 128;
                }
            } else {
                byte[] result = cipher.doFinal(datas);
                byteList.addAll(Bytes.asList(result));
            }

            return new String(Bytes.toArray(byteList), UTF_8);
        } catch (Exception e) {
            logger.error("rsa decrypt error", e);
            throw new RuntimeException("rsa decrypt error");
        }
    }


    private static class RsaKeyPair {
        private String publicKey = "";
        private String privateKey = "";

        public RsaKeyPair(String publicKey, String privateKey) {
            super();
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }
        public String getPrivateKey() {
            return privateKey;
        }
    }
}
