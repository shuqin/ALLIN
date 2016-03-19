package cc.lovesq.invoker.impl;

import cc.lovesq.invoker.constants.InvokerMsg;
import cc.lovesq.result.Result;

public class InvokeResult {

	public static final Integer successCode = 200;
	public static final String successMsg = "Successful";
    public static final InvokeResult errorResult = new InvokeResult("Network Error", -1, Result.errorResultForNetworkError);
    public static final InvokeResult errorResultForIllegalParam = new InvokeResult("Param Error", -1, Result.errorResultForParamsError);
    public static final InvokeResult successResult = new InvokeResult(successMsg, successCode, Result.successResult);
	
    private int                code;
    private String             msg;
    private Result             result;

    public InvokeResult() {
    }

    public InvokeResult(String msg, Integer code, Result result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return code == InvokerMsg.SUCCESS_STATUS;
    }
}
