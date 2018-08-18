/**
 *
 */
package cc.lovesq.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author zhoukun.zk
 */
public class GsonUtil {

  static GsonBuilder gsonBuilder = null;

  static {
    gsonBuilder = new GsonBuilder();
    gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
  }

  public static Gson getGson() {
    return gsonBuilder.create();
  }
}
