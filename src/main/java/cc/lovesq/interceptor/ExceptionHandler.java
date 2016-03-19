package cc.lovesq.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cc.lovesq.base.AjaxResult;
import cc.lovesq.base.AjaxResultExt;
import cc.lovesq.exception.BizException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;


import java.util.HashMap;
import java.util.Map;

public class ExceptionHandler implements HandlerExceptionResolver {

	private final Log logger = LogFactory.getLog(ExceptionHandler.class);

	@SuppressWarnings("unchecked")
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		response.setStatus(HttpServletResponse.SC_OK);
        if(isAjaxRequest(handler)) {

            AjaxResult ajaxResult = exceptionToAjaxResult(ex);
            if(isAjaxRequestReturnAjaxResultExt(handler)){
                ModelAndView model = new ModelAndView(new MappingJacksonJsonView());
                AjaxResultExt ajaxResultExt = AjaxResultExt.toAjaxResultExt(ajaxResult);
                model.addObject("success", ajaxResultExt.isSuccess());
                model.addObject("message", ajaxResultExt.getMessage());
                model.addObject("data", ajaxResultExt.getData());
                return model;
            }else {
                ModelAndView model = new ModelAndView(new MappingJacksonJsonView());
                model.addObject("data", ajaxResult.getData());
                model.addObject("info", ajaxResult.getInfo());
                return model;
            }
        }else{
            String message = StringUtils.EMPTY;

            if (ex instanceof BizException) {
                message = ex.getMessage();
            } else {
                message = "未知异常,错误信息：" + ex.getMessage();
                logger.error(ex.getMessage(), ex);
            }
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", message);
            map.put("stackTrace", stackTraceElements);
            map.put("exception", ex);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addAllObjects(map);
            modelAndView.setViewName("error");
            return modelAndView;
        }
	}

    private AjaxResult exceptionToAjaxResult(Exception ex){
        AjaxResult ajaxResult = null;
        if (ex instanceof BizException) {
            ajaxResult = AjaxResult.errorResult(ex.getMessage());
        } else if (ex instanceof IllegalArgumentException) {
            ajaxResult = AjaxResult.errorResult(ex.getMessage());
        } else {
            ajaxResult = AjaxResult.errorResult("未知异常");
            logger.error(ex.getMessage(), ex);
        }
        return ajaxResult;
    }

    public boolean isAjaxRequest(Object handler){
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            boolean hasResponseBody = (null == handlerMethod.getMethodAnnotation(ResponseBody.class)) ? false : true;
            boolean isAjaxResult = handlerMethod.getReturnType().getParameterType().isAssignableFrom(AjaxResult.class);
            boolean isAjaxResultExt = handlerMethod.getReturnType().getParameterType().isAssignableFrom(AjaxResultExt.class);
            if(hasResponseBody && (isAjaxResult || isAjaxResultExt)){
                return true;
            }
        }
        return false;
    }

    private boolean isAjaxRequestReturnAjaxResultExt(Object handler){
        if(isAjaxRequest(handler)){
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            boolean isAjaxResultExt = AjaxResultExt.class.isAssignableFrom(handlerMethod.getReturnType().getParameterType());
            return isAjaxResultExt;
        }
        return false;
    }
}
