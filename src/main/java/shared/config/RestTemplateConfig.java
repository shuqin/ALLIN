package shared.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import shared.utils.DateTimeUtils;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * RestTemplate配置
 * Create by wenjin.xu on 2023/1/12
 */
public class RestTemplateConfig {

    // 连接池的最大连接数
    private static final int MAX_TOTAL_CONNECT = 100;

    // 每个路由（主机+端口）的最大连接数
    private static final int MAX_CONNECT_PER_ROUTE = 100;

    // 连接超时时间，单位：毫秒
    private static final int CONNECT_TIMEOUT = 5000;

    // 读取超时时间，单位：毫秒
    private static final int READ_TIMEOUT = 20000;

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    static {
        REST_TEMPLATE.setRequestFactory(createFactory());
        List<HttpMessageConverter<?>> converterList = REST_TEMPLATE.getMessageConverters();

        // 设置StringHttpMessageConverter字符集为UTF-8，解决中文乱码问题
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (StringHttpMessageConverter.class == item.getClass()) {
                converterTarget = item;
                break;
            }
        }
        if (null != converterTarget) {
            converterList.remove(converterTarget);
        }
        converterList.add(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        converterList.add(jsonConverter());
    }

    /**
     * 单例模式返回 RestTemplate
     */
    public static RestTemplate getRestTemplate() {
        return REST_TEMPLATE;
    }

    private static ClientHttpRequestFactory createFactory() {
        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(MAX_TOTAL_CONNECT)
                .setMaxConnPerRoute(MAX_CONNECT_PER_ROUTE)
                .build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setConnectTimeout(CONNECT_TIMEOUT);
        factory.setReadTimeout(READ_TIMEOUT);
        return factory;
    }

    private static MappingJackson2HttpMessageConverter jsonConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtils.DATE_TIME));
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }

}