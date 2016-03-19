package cc.lovesq.result;

import java.io.Serializable;
import java.util.List;

import cc.lovesq.util.GsonUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * @Described：JSON返回通用接口
 * @author YHJ create at 2014年3月4日 下午4:05:45
 */
public class JsonResult implements Serializable{

	/**
	 * @FieldInfo JsonResult.java：long
	 * @Author YHJ create at 2014年1月18日 下午3:29:28
	 */
	private static final long serialVersionUID = -4478525014036172802L;
	
	private List<?> data;
	private int code;
	private int total;
	private String msg;
	
	public JsonResult() {
	}
	
	public JsonResult(List<?> data) {
		super();
		this.code = 200;
		this.data = data;
		if(data!=null)
			this.total = data.size();
	}
	
	
	public JsonResult(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public <T> List<T> toListData(TypeToken<?> token){
		Gson gson = GsonUtil.getGson();
		String dataStr = gson.toJson(data);
		return gson.fromJson(dataStr, token.getType());
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getFirstData(Class<T> clazz){
		if(data != null && data.size() > 0){
			if(clazz.equals(String.class))
				return (T)data.get(0);
			Gson gson = GsonUtil.getGson();
			return gson.fromJson(gson.toJson(data.get(0)),clazz);
		}
		return null;
	}
	

	public List<?> getData() {
		return data;
	}


	public void setData(List<?> data) {
		this.data = data;
	}


	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
		
	public boolean isSuccess(){
		return this.code == 200;
	}

}

