package cc.lovesq.constants;

import cc.lovesq.exception.BizException;

public enum ErrorCodeEmun {
	
	ERROR_ARGUMENT(400, IllegalArgumentException.class, "参数错误", true),

	ERROR_BIZ(401, BizException.class, "业务异常", true),

	ERROR_UNKNOW(409, Exception.class, "系统错误", false);

	/** 错误码 */
	private int code;

	/** 异常类. */
	private Class<?> clazz;

	/** 简要错误信息. */
	private String msg;

	/** 是否显示详细的错误信息. */
	private boolean isShowError;

	private ErrorCodeEmun(int code, Class<?> clazz, String msg, boolean isShowError) {
		this.code = code;
		this.clazz = clazz;
		this.msg = msg;
		this.isShowError = isShowError;
	}

	public static ErrorCodeEmun getByClass(Class<?> clazz) {
		for (ErrorCodeEmun errorCodeEmun : ErrorCodeEmun.values()) {
			if (errorCodeEmun.getClazz().equals(clazz)) {
				return errorCodeEmun;
			}
		}
		return ErrorCodeEmun.ERROR_UNKNOW;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isShowError() {
		return isShowError;
	}

	public void setShowError(boolean isShowError) {
		this.isShowError = isShowError;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}