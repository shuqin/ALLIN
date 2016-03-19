package cc.lovesq.util;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.lang.StringUtils;
import org.apache.xml.security.exceptions.Base64DecodingException;
import org.apache.xml.security.utils.Base64;

public enum SignatureAlgorithm {
    /**
     * 签名方式为MD5的签名机制安全实现
     */
    MD5() {

        public String sign(String key, String data) throws SignatureException {
            try {
                byte[] theDate = getDataToSign(data + key);
                byte[] result = Hashes.Digest.MD5.get().digest(theDate);
                return Base64.encode(result);
            } catch (NoSuchAlgorithmException e) {
                throw new SignatureException("签名算法不支持", e);
            } catch (UnsupportedEncodingException e) {
                throw new SignatureException("处理待签名数据时发生错误", e);
            }

        }

    },
    /**
     * 签名方式为DSA的签名机制安全实现
     */
    DSA() {

        public String sign(String priKey, String data) throws SignatureException {

            try {
                KeyFactory keyFactory = KeyFactory.getInstance(this.name());
                byte[] originalKey = Base64.decode(priKey);
                EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(originalKey);
                PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
                Signature signature = Signature.getInstance(this.name());
                signature.initSign(privateKey);
                signature.update(getDataToSign(data));
                byte[] signed = signature.sign();
                return Base64.encode(signed);
            } catch (NoSuchAlgorithmException e) {
                throw new SignatureException("签名算法不支持", e);
            } catch (Base64DecodingException e) {
                throw new SignatureException("读取Key时发生错误", e);
            } catch (InvalidKeySpecException e) {
                throw new SignatureException("无效的私钥", e);
            } catch (InvalidKeyException e) {
                throw new SignatureException("无效的私钥", e);
            } catch (UnsupportedEncodingException e) {
                throw new SignatureException("处理待签名数据时发生错误 ", e);
            } catch (SignatureException e) {
                throw new SignatureException("签名时发生异常", e);
            }
        }

        public boolean verify(String pubKey, String data, String signature) throws SignatureException {
            try {
                KeyFactory keyFactory = KeyFactory.getInstance(this.name());
                byte[] originalKey = Base64.decode(pubKey);
                EncodedKeySpec keySpec = new X509EncodedKeySpec(originalKey);
                PublicKey publicKey = keyFactory.generatePublic(keySpec);
                Signature signCheck = Signature.getInstance(this.name());
                signCheck.initVerify(publicKey);
                signCheck.update(getDataToSign(data));
                return signCheck.verify(Base64.decode(signature));
            } catch (NoSuchAlgorithmException e) {
                throw new SignatureException("签名算法不支持", e);
            } catch (Base64DecodingException e) {
                throw new SignatureException("读取Key时发生错误", e);
            } catch (InvalidKeySpecException e) {
                throw new SignatureException("无效的公钥", e);
            } catch (InvalidKeyException e) {
                throw new SignatureException("无效的公钥", e);
            } catch (UnsupportedEncodingException e) {
                throw new SignatureException("处理待签名数据时发生错误 ", e);
            } catch (SignatureException e) {
                throw new SignatureException("验证签名时发生异常", e);
            }
        }

        public boolean isSymmetric() {
            return false;
        }
    };

    SignatureAlgorithm() {

    }

    /**
     * 签名
     * 
     * @param key 钥匙
     * @param data 数据
     * @return 签名结果
     * @throws SignatureException
     */
    public abstract String sign(String key, String data) throws SignatureException;

    /**
     * 签名结果验证
     * 
     * @param key 钥匙
     * @param data 数据
     * @param signature 待验证签名结果
     * @return 是否ok
     * @throws SignatureException
     */
    public boolean verify(String key, String data, String signature) throws SignatureException {
        String curSignature = this.sign(key, data);
        if (StringUtils.equals(curSignature, signature)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否是对称算法
     * 
     * @return
     */
    public boolean isSymmetric() {
        return true;
    }

    /**
     * 根据算法名称获取签名对象
     * 
     * @param algorithmName
     * @return
     */
    public static SignatureAlgorithm get(String algorithmName) {
        for (SignatureAlgorithm algorithm : SignatureAlgorithm.values()) {
            if (StringUtils.endsWithIgnoreCase(algorithm.name(), algorithmName)) {
                return algorithm;
            }
        }
        return null;
    }

    /**
     * 取得指定content的字节串。
     * 
     * @throws UnsupportedEncodingException
     * @throws DSAException
     */
    private static byte[] getDataToSign(String content) throws UnsupportedEncodingException {
        String charset = getCanonicalCharset(new OutputStreamWriter(new ByteArrayOutputStream()).getEncoding(), "UTF-8");
        return content.getBytes(charset);

    }

    /**
     * 取得正规的字符集名称, 如果指定字符集不存在, 则返回<code>null</code>.
     * 
     * @param charset 字符集名称
     * @return 正规的字符集名称, 如果指定字符集不存在, 则返回<code>null</code>
     */
    private static String getCanonicalCharset(String charset, String defaultCharset) {
        String result = null;

        try {
            result = Charset.forName(defaultCharset).name();
        } catch (IllegalArgumentException e) {
            if (defaultCharset != null) {
                try {
                    result = Charset.forName(defaultCharset).name();
                } catch (IllegalArgumentException ee) {
                }
            }
        }
        return result;
    }
}
