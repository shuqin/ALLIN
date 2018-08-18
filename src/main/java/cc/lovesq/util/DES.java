package cc.lovesq.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DES {

  private static final String PASSWORD_CRYPT_KEY = "xxx_xxxx";

  private final static String DES = "DES";

  /**
   * 加密
   *
   * @param src 数据源
   * @param key 密钥，长度必须是8的倍数
   * @return 返回加密后的数据
   */
  private static byte[] encrypt(byte[] src, byte[] key) throws Exception {

    SecureRandom sr = new SecureRandom();
    DESKeySpec dks = new DESKeySpec(key);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
    SecretKey securekey = keyFactory.generateSecret(dks);
    Cipher cipher = Cipher.getInstance(DES);
    cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
    return cipher.doFinal(src);
  }

  /**
   * 解密
   *
   * @param src 数据源
   * @param key 密钥，长度必须是8的倍数
   * @return 返回解密后的原始数据
   */
  private static byte[] decrypt(byte[] src, byte[] key) throws Exception {

    SecureRandom sr = new SecureRandom();
    DESKeySpec dks = new DESKeySpec(key);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
    SecretKey securekey = keyFactory.generateSecret(dks);
    Cipher cipher = Cipher.getInstance(DES);
    cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
    return cipher.doFinal(src);
  }

  /**
   * 二进制转字符串
   */
  private static String byte2hex(byte[] b) {
    String hs = "";
    String stmp = "";
    for (int n = 0; n < b.length; n++) {
      stmp = (java.lang.Integer.toHexString(b[n] & 0xFF));
      if (stmp.length() == 1) {
        hs = hs + "0" + stmp;
      } else {
        hs = hs + stmp;
      }
    }
    return hs.toUpperCase();
  }

  private static byte[] hex2byte(byte[] b) {
    if ((b.length % 2) != 0) {
      throw new IllegalArgumentException("DES解密字节长度不是偶数!");
    }
    byte[] b2 = new byte[b.length / 2];
    for (int n = 0; n < b.length; n += 2) {
      String item = new String(b, n, 2);
      b2[n / 2] = (byte) Integer.parseInt(item, 16);
    }
    return b2;
  }

  /**
   * 数据解密
   */
  public final static byte[] decrypt(byte[] bytes) throws Exception {

    return decrypt(hex2byte(bytes), PASSWORD_CRYPT_KEY.getBytes());
  }

  /**
   * 数据加密
   */
  public final static String encrypt(byte[] bytes) throws Exception {

    return byte2hex(encrypt(bytes, PASSWORD_CRYPT_KEY.getBytes()));
  }

}
