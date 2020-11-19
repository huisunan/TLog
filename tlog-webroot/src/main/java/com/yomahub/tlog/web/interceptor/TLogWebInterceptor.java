package com.yomahub.tlog.web.interceptor;

import com.yomahub.tlog.web.common.TLogWebCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * web controller的拦截器
 *
 * @author Bryan.Zhang
 * @since 1.1.5
 */
public class TLogWebInterceptor extends AbsTLogWebHandlerMethodInterceptor {

    private static final Logger log = LoggerFactory.getLogger(TLogWebInterceptor.class);

    @Override
    public boolean preHandleByHandlerMethod(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        TLogWebCommon.loadInstance().preHandle(request, response, handler);
        return true;
    }

    @Override
    public void postHandleByHandlerMethod(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletionByHandlerMethod(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TLogWebCommon.loadInstance().afterCompletion(request, response, handler);
    }

}
