package cc.lovesq.result;

import cc.lovesq.invoker.constants.InvokerMsg;

public class Result {

    public static final String  successMsg                 = "successful";
    public static final Integer successCode                = 200;
    public static final Result  errorResultForNetworkError = new Result("网络错误", -1);
    public static final Result  errorResultForParamsError  = new Result("参数错误", -1);
    public static final Result  successResult              = new Result(successMsg, successCode);
    public static final Result  emptyResult                = new Result("[Null]", 0);
    private String        msg                        = Result.successMsg;
    private Integer       code                       = 200;
    private Object        data;

    public Result() {
    }

    public Result(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public boolean isSuccess() {
        return code == InvokerMsg.SUCCESS_STATUS;
    }
}
