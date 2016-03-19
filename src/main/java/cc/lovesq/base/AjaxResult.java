package cc.lovesq.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AjaxResult {
	private Info info;

	@SuppressWarnings("rawtypes")
	private Map data;

	public AjaxResult(boolean isOk) {
		this.info = new Info(isOk);
	}

	public Info getInfo() {
		return info;
	}

	@SuppressWarnings("rawtypes")
	public Map getData() {
		if (data == null) {
			data = new HashMap();
		}
		return data;
	}

	@SuppressWarnings("rawtypes")
	public void setData(Map data) {
		this.data = data;
	}

	public static AjaxResult succResult() {
		return new AjaxResult(true);
	}

	public static AjaxResult succResult(String message) {
		AjaxResult result = new AjaxResult(true);
		result.getInfo().setMessage(message);
		return result;
	}

	/**
	 * 工具函数，生成一个返回对象
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static AjaxResult succResult(String key, Object value) {
		AjaxResult result = new AjaxResult(true);
		Map data = new HashMap<String, Object>();
		data.put(key, value);
		result.setData(data);
		return result;
	}

	public static AjaxResult errorResult(String message) {
		AjaxResult result = new AjaxResult(false);
		result.getInfo().setMessage(message);
		return result;
	}
	

	
	public static AjaxResult errorResult() {
		AjaxResult result = new AjaxResult(false);
		result.getInfo().setMessage("system.error");
		return result;
	}

	@SuppressWarnings("rawtypes")
	public static AjaxResult succResult(Map dataMap) {
		AjaxResult result = new AjaxResult(true);
		result.setData(dataMap);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public static AjaxResult pagerResult(Object pager, List list) {
		AjaxResult result = new AjaxResult(true);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);
		data.put("pager", pager);
		result.setData(data);
		return result;
	}

	public String toSimpleJsonString() {
		return "{\"info\":{\"message\":\"" + info.getMessage() + "\"},\"data\":{}}";
	}
}