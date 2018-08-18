package cc.lovesq.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Array;

public class Hashes {

  public enum Digest {
    GOST3411, Tiger, Whirlpool, MD2, MD4, MD5, RipeMD128, RipeMD160, RipeMD256, RipeMD320, SHA1, SHA224, SHA256,
    SHA384, SHA512;

    public MessageDigest get() throws NoSuchAlgorithmException {
      return MessageDigest.getInstance(this.name());
    }
  }

  public static String getSignedData(Map<String, Object> params) {
    List<String> keys = new ArrayList<String>(params.keySet());
    // 去掉签名值和签名方式
    Collections.sort(keys);
    StringBuffer buffer = new StringBuffer();
    String key, value;
    for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
      key = (String) i.next();
      value = getParameterValue(params.get(key));
      buffer.append("&").append(key).append("=").append(value);
    }
    return buffer.substring(1);
  }

  public static String getParameterValue(Object value) {
    if (value == null) {
      return null;
    }
    if (value.getClass().isArray()) {
      return String.valueOf(Array.get(value, 0));
    }
    return String.valueOf(value);
  }

}
