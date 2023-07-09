package shared.utils;

import cc.lovesq.exception.BizException;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import shared.config.RestTemplateConfig;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static cc.lovesq.exception.Errors.ServerError;

/**
 * Rest请求-工具类
 * Create by wenjin.xu on 2023/1/12
 */
public class RestUtils {

    private static final Logger LOG = LoggerFactory.getLogger(RestUtils.class);

    private static final String ACCEPT = "Accept";

    /**
     * 发送post请求
     *
     * @param url       请求url
     * @param headerMap 请求头
     * @param bodyJson  请求体
     * @return 返回json字符串
     */
    public static <T> T post(String url, Map<String, String> headerMap, String bodyJson, Class<T> cls) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        if (MapUtils.isNotEmpty(headerMap)) {
            headerMap.forEach(headers::add);
        }

        RequestEntity<String> requestEntity = RequestEntity
                .post(new URI(url))
                .header(ACCEPT, MediaType.APPLICATION_JSON.toString())
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(bodyJson);
        ResponseEntity<T> responseEntity = RestTemplateConfig.getRestTemplate().exchange(requestEntity, cls);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            String errorInfo = String.format("post failed, url:%s, code:%s, response:%s", url, responseEntity.getStatusCode(), responseEntity.getBody());
            LOG.error("execute post request error, errorInfo={}", errorInfo);
            throw new BizException(ServerError);
        }
        return responseEntity.getBody();
    }

    /**
     * 发送get请求
     *
     * @param url       请求url
     * @param headerMap 请求头
     * @return 返回json字符串
     */
    public static <T> T get(String url, Map<String, String> headerMap, Class<T> cls) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        if (MapUtils.isNotEmpty(headerMap)) {
            headerMap.forEach(headers::add);
        }

        RequestEntity<?> requestEntity = RequestEntity
                .get(new URI(url))
                .header(ACCEPT, MediaType.APPLICATION_JSON.toString())
                .headers(headers)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        ResponseEntity<T> responseEntity = RestTemplateConfig.getRestTemplate().exchange(requestEntity, cls);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            String errorInfo = String.format("get failed, url:%s, code:%s, response:%s", url, responseEntity.getStatusCode(), responseEntity.getBody());
            LOG.error("execute get request error, errorInfo={}", errorInfo);
            throw new BizException(ServerError);
        }
        return responseEntity.getBody();
    }

}
