package cc.lovesq.invoker.impl;

import java.util.Map;

import cc.lovesq.invoker.constants.HttpMethod;

public class InvokeRequest {

    private HttpMethod          httpMethod;
    private Map<String, String> params;
    private String              regionNo;
    private String              servletPath;
    private String 				url;

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getRegionNo() {
        return regionNo;
    }

    public void setRegionNo(String regionNo) {
        this.regionNo = regionNo;
    }

    public String getServletPath() {
        return servletPath;
    }

    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
