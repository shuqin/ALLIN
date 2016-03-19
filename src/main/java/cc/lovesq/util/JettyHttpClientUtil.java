package cc.lovesq.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.io.AbstractBuffer;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import cc.lovesq.invoker.constants.HttpMethod;
import cc.lovesq.invoker.constants.InvokerMsg;
import cc.lovesq.invoker.impl.InvokeResult;

public class JettyHttpClientUtil {

    private static Logger     logger                   = Logger.getLogger(JettyHttpClientUtil.class);

    public static HttpClient  httpClient               = null;
    private static final int  maxConnectionsPerAddress = 10;
    private static final int  maxThreads               = 50;
    private static final long timeout                  = 60000;

    static {
        httpClient = new HttpClient();
        httpClient.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
        httpClient.setMaxConnectionsPerAddress(maxConnectionsPerAddress);
        httpClient.setThreadPool(new QueuedThreadPool(maxThreads));
        httpClient.setTimeout(timeout);
        try {
            httpClient.start();
        } catch (Exception e) {
            throw new RuntimeException("JettyHttpClient initialize fail!", e);
        }
    }

    public static InvokeResult getData(String url) {
        if (StringUtils.isBlank(url)) {
            return InvokeResult.errorResult;
        }
        ContentExchange contentExchange = new ContentExchange();
        contentExchange.setMethod(HttpMethod.GET.getValue());
        logger.info(url);
        contentExchange.setURL(url);
        return invoke(contentExchange);
    }

    public static InvokeResult postData(String url, String params) {
        if (StringUtils.isBlank(url) || StringUtils.isBlank(params)) {
            return InvokeResult.errorResult;
        }
        ContentExchange contentExchange = new ContentExchange();
        contentExchange.setMethod(HttpMethod.POST.getValue());
        contentExchange.setURL(url);
        try {
            AbstractBuffer content = new ByteArrayBuffer(params.getBytes(InvokerMsg.CHARSET_UTF8));
            contentExchange.setRequestContent(content);
            return invoke(contentExchange);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        return InvokeResult.errorResult;
    }

    public static InvokeResult putData(String url, String params) {
        if (StringUtils.isBlank(url) || StringUtils.isBlank(params)) {
            return InvokeResult.errorResult;
        }
        ContentExchange contentExchange = new ContentExchange();
        contentExchange.setMethod(HttpMethod.PUT.getValue());
        contentExchange.setURL(url);
        try {
            AbstractBuffer content = new ByteArrayBuffer(params.getBytes(InvokerMsg.CHARSET_UTF8));
            contentExchange.setRequestContent(content);
            return invoke(contentExchange);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        return InvokeResult.errorResult;
    };

    public static InvokeResult deleteData(String url) {
        if (StringUtils.isBlank(url)) {
            return InvokeResult.errorResult;
        }
        ContentExchange contentExchange = new ContentExchange();
        contentExchange.setMethod(HttpMethod.DELETE.getValue());
        contentExchange.setURL(url);
        return invoke(contentExchange);
    };

    private static InvokeResult invoke(ContentExchange contentExchange) {
        String requestUrl = "[url:" + contentExchange.getURI()+"]";
    	try {
        	logger.info(contentExchange);
            httpClient.send(contentExchange);
            logger.info(contentExchange);
        } catch (IOException e) {
            logger.error(e.getMessage() + " " + requestUrl + " Http Request Send Failed!", e);
            return InvokeResult.errorResult;
        }
        try {
            contentExchange.waitForDone();
            logger.info(contentExchange);
        } catch (InterruptedException e) {
            logger.error(e.getMessage() + "[Http Request status: " + contentExchange + "]", e);
            return InvokeResult.errorResult;
        }
        InvokeResult invokerResult = new InvokeResult();
        invokerResult.setCode(contentExchange.getResponseStatus());
        try {
            invokerResult.setMsg(contentExchange.getResponseContent());
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        return invokerResult;
    }
}
