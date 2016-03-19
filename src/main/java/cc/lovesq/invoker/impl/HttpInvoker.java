package cc.lovesq.invoker.impl;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cc.lovesq.constants.LogConstant;
import cc.lovesq.invoker.constants.HttpMethod;
import cc.lovesq.invoker.constants.InvokerMsg;
import cc.lovesq.util.JettyHttpClientUtil;

// 老嫦娥调用API入口：opstools.moonmm.vm.flex.OperatingFlex.templatedApicall(Map<String, Object>, Map<String, Object>, boolean)
public abstract class HttpInvoker implements HttpInvoke {

    public InvokeResult invoke(InvokeRequest request) {
        if (isParamsIllegal(request)) {
            return InvokeResult.errorResultForIllegalParam;
        }
        InvokeResult invokeResult = null;
        if (request.getHttpMethod().equals(HttpMethod.GET)) {
        	String url = buildGetUrl(request);
        	System.out.println(url);
            invokeResult = JettyHttpClientUtil.getData(url);
            if (url.contains("sls")) {
            	// sls 获取的数据量太大, 打日志会影响服务器性能
            	LogConstant.info("Url: " + url + "\nResult code: " + invokeResult.getCode());
            	//logger.info("Url: " + url + "\nResult: " + invokeResult.getMsg());
            }
            else {
            	LogConstant.info("Url: " + url + "\nResult: " + invokeResult.getMsg());
            }
            
        }
        if (request.getHttpMethod().equals(HttpMethod.POST)) {
            invokeResult = JettyHttpClientUtil.postData(getPartnerUrl(request), preProcessParams(request));
        }
        if(!invokeResult.isSuccess())
        	LogConstant.error("Call houyi API ERROR:Code="+invokeResult.getCode()+",MSG="+invokeResult.getMsg());
        return invokeResult;
    }

    protected abstract boolean isParamsIllegal(InvokeRequest request);

    protected abstract String getPartnerUrl(InvokeRequest request);

    protected abstract String preProcessParams(InvokeRequest request);

    protected String appendUrlParamsAscSorted(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        StringBuffer buffer = new StringBuffer();

        String key, value;
        for (Iterator<String> i = keys.iterator(); i.hasNext();) {
            key = (String) i.next();
            value = getParameterValue(params.get(key));
            if (StringUtils.isNotBlank(value)) {
                buffer.append("&").append(key);
                try {
                	if (key.equals("dimensions") || key.equals("port_range") || key.equals("source_cidr_ip")) {
                		buffer.append("=").append(String.valueOf(value));
                	}
                	else {
                		buffer.append("=").append(URLEncoder.encode(String.valueOf(value), InvokerMsg.CHARSET_UTF8));
                	}
                    
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("HttpInvoker encode params error!", e);
                }
            }
        }
        return buffer.substring(1);
    }

    protected String appendParamsAscSorted(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        StringBuffer buffer = new StringBuffer();
        String key, value;
        for (Iterator<String> i = keys.iterator(); i.hasNext();) {
            key = (String) i.next();
            value = getParameterValue(params.get(key));
            buffer.append("&").append(key).append("=").append(value);
        }
        return buffer.substring(1);
    }

    private String getParameterValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value.getClass().isArray()) {
            return String.valueOf(Array.get(value, 0));
        }
        return String.valueOf(value);
    }

    private String buildGetUrl(InvokeRequest request) {
        StringBuffer url = new StringBuffer(getPartnerUrl(request));
        if (StringUtils.isNotBlank(request.getServletPath())) {
            url.append(request.getServletPath());
        }
        url.append("?");
        url.append(preProcessParams(request));
        return url.toString();
    }

}
