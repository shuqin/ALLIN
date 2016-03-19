package cc.lovesq.invoker.impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import cc.lovesq.invoker.param.SLSParams;

public class SlsApiInvoker extends HttpInvoker {

    private String url;
    private String userId;
    private String project;
    private String version;
	/** 璁块棶SLS鐨処D**/
	private String accessId;
	/** 璁块棶SLS鐨凨ey**/
	private String accessKey;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    protected String getPartnerUrl(InvokeRequest request) {
        return url;
    }

    @Override
    protected boolean isParamsIllegal(InvokeRequest request) {
        Map<String, String> params = request.getParams();
        if (StringUtils.isBlank(params.get(SLSParams.KEY_SLS_CATEGORY))) {
            return true;
        }
        if (StringUtils.isBlank(params.get(SLSParams.KEY_SLS_BEGIN_TIME))) {
            return true;
        }
        if (StringUtils.isBlank(params.get(SLSParams.KEY_SLS_END_TIME))) {
            return true;
        }
        return false;
    }

    @Override
    protected String preProcessParams(InvokeRequest request) {
        Map<String, String> params = request.getParams();
        params.put(cc.lovesq.invoker.param.SLSParams.KEY_SLS_ACCESS_KEY_ID, this.getAccessId());
        params.put(SLSParams.KEY_SLS_PROJECT, this.getProject());
        params.put(SLSParams.KEY_SLS_VERSION, this.getVersion());
//      params.put("Reverse", "false");
        params.put(SLSParams.SLS_GETDATA_SIGN_PARAM_NAME, getSignature(getAccessKey(), SLSParams.SLS_GETDATA_SIGN_KEY, buildUrlParameter(params)));
        return appendUrlParamsAscSorted(request.getParams());
    }
    
    private String getSignature(String accesskey, String verb, String urlParas) {
		StringBuilder builder = new StringBuilder();
		builder.append("/").append(verb).append("\n").append(urlParas);
		try {
			byte[] keyBytes = accesskey.getBytes("UTF-8");
			byte[] dataBytes = builder.toString().getBytes(
					"UTF-8");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(new SecretKeySpec(keyBytes, "HmacSHA1"));
			return new String(Base64.encodeBase64(mac.doFinal(dataBytes)));
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Not Supported encoding method UTF-8", 
					e);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Not Supported signature method HmacSHA1", 
					e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException("Failed to calcuate the signature", e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String buildUrlParameter(Map<String, String> paras) {
		Map treeMap = new TreeMap(paras);
		StringBuilder builder = new StringBuilder();
		boolean isFirst = true;
		for (Iterator localIterator = treeMap.entrySet().iterator(); localIterator.hasNext(); ) { 
		Map.Entry entry = (Map.Entry)localIterator.next();
		if (isFirst)
			isFirst = false;
		else
			builder.append("&");

		builder.append((String)entry.getKey()).append("=").append((String)entry.getValue());
		}
		return builder.toString();
	}

	public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}
}
