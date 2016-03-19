package cc.lovesq.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cc.lovesq.web.ThreadLocalManager;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    private final Log        logger     = LogFactory.getLog(ExceptionHandler.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        doLogin(request, response);
        return true;
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

    }

    private String getCurrentRequestURL(HttpServletRequest request) {
        StringBuilder location = new StringBuilder(48);
        String scheme = request.getScheme();
        int port = request.getServerPort();

        location.append(scheme);
        location.append("://");
        location.append(request.getServerName());
        if (port > 0
                && ((scheme.equalsIgnoreCase("http") && port != 80) || (scheme
                        .equalsIgnoreCase("https") && port != 443))) {
            location.append(':');
            location.append(port);
        }

        if (StringUtils.isNotEmpty(request.getContextPath())) {
            String uri = StringUtils.replaceOnce(request.getRequestURI(), request.getContextPath(),
                    "");
            location.append(uri);
        } else {
            location.append(request.getRequestURI());
        }

        if (StringUtils.isNotBlank(request.getQueryString())) {
            location.append("?").append(removeNamedRequestParameter(request, "abcUserToken"));
        }
        return location.toString();
    }

    private String removeNamedRequestParameter(HttpServletRequest request, String paramName) {
        Enumeration<String> parameterNames = request.getParameterNames();
        if (null == parameterNames) {
            return StringUtils.EMPTY;
        }
        List<String> nameValuePairString = new ArrayList<String>();
        while (parameterNames.hasMoreElements()) {
            if (!StringUtils.equals(paramName, parameterNames.nextElement())) {
                nameValuePairString.add(paramName + "=" + request.getParameter(paramName));
            }
        }
        return StringUtils.join(nameValuePairString, "&");
    }

    private String findCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (null == cookies || 0 == cookies.length) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (StringUtils.equals(cookie.getName(), cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        ThreadLocalManager.clear();
    }

}
