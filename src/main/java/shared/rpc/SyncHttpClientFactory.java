package shared.rpc;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.nio.charset.Charset;

public class SyncHttpClientFactory {

    private static final int DEFAULT_MAX_TOTAL = 512;

    private static final int DEFAULT_MAX_PER_ROUTE = 64;

    private static final int DEFAULT_CONNECTION_TIMEOUT = 5000;

    private static final int DEFAULT_SOCKET_TIMEOUT = 3000;

    public static CloseableHttpClient getInstance() {
        ConnectionConfig config =
                ConnectionConfig.custom().setCharset(Charset.forName("utf-8")).build();
        RequestConfig
                defaultRequestConfig =
                RequestConfig.custom().setConnectionRequestTimeout(DEFAULT_CONNECTION_TIMEOUT)
                        .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT).build();
        return HttpClients
                .custom().setMaxConnPerRoute(DEFAULT_MAX_PER_ROUTE).setMaxConnTotal(DEFAULT_MAX_TOTAL)
                .setDefaultConnectionConfig(config).setDefaultRequestConfig(defaultRequestConfig).build();
    }

}
