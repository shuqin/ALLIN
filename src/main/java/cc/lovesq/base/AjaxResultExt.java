package cc.lovesq.base;

/**
 * @author shiyou.xusy
 * @since 15/2/6.
 */
public class AjaxResultExt extends AjaxResult {

    public AjaxResultExt(boolean isOk){
        super(isOk);
    }

    public static AjaxResultExt succResultExt(String message){
        AjaxResultExt ajaxResultExt = new AjaxResultExt(true);
        ajaxResultExt.setMessage(message);
        return ajaxResultExt;
    }

    public static AjaxResultExt errorResultExt(String message){
        AjaxResultExt ajaxResultExt = new AjaxResultExt(false);
        ajaxResultExt.setMessage(message);
        return ajaxResultExt;
    }

    public static AjaxResultExt toAjaxResultExt(AjaxResult ajaxResult){
        Info info = ajaxResult.getInfo();
        AjaxResultExt ajaxResultExt = new AjaxResultExt(info.isOk());
        ajaxResultExt.setMessage(info.getMessage());
        return ajaxResultExt;
    }

    public boolean isSuccess() {
        return getInfo().isOk();
    }

    public void setSuccess(boolean success) {
        getInfo().setOk(success);
    }

    public String getMessage() {
        return getInfo().getMessage();
    }

    public void setMessage(String message) {
        getInfo().setMessage(message);
    }
}
