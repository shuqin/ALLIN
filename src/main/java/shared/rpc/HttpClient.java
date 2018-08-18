package shared.rpc;

import com.alibaba.fastjson.JSONObject;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component("httpClient")
public class HttpClient {

  private static Logger logger = LoggerFactory.getLogger(HttpClient.class);

  private CloseableHttpClient syncHttpClient = SyncHttpClientFactory.getInstance();

  /**
   * 向 ES 发送查询请求获取结果
   */
  public JSONObject query(String query, String url) throws Exception {
    StringEntity entity = new StringEntity(query, "utf-8");
    HttpPost post = new HttpPost(url);
    Header header = new BasicHeader("Content-Type", "application/json");
    post.setEntity(entity);
    post.setHeader(header);

    CloseableHttpResponse resp = null;
    JSONObject rs = null;
    try {
      resp = syncHttpClient.execute(post);
      int code = resp.getStatusLine().getStatusCode();
      HttpEntity respEntity = resp.getEntity();
      String response = EntityUtils.toString(respEntity, Charset.forName("utf-8"));

      if (code != 200) {
        logger.warn("request failed resp:{}", response);
      }
      rs = JSONObject.parseObject(response);
    } finally {
      if (resp != null) {
        resp.close();
      }
    }
    return rs;
  }


}
