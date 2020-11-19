package com.yomahub.tlog.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.yomahub.tlog.context.TLogContext;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TLogWebInvokeTimeInterceptor extends TLogWebHandlerMethodInterceptor {

    private static final Logger log = LoggerFactory.getLogger(TLogWebInvokeTimeInterceptor.class);

    private InheritableThreadLocal<StopWatch> invokeTimeTL = new InheritableThreadLocal<>();

    @Override
    public boolean preHandleByHandlerMethod(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (TLogContext.enableInvokeTimePrint()) {
            String url = request.getRequestURI();
            String parameters = JSON.toJSONString(request.getParameterMap());
            log.info("[TLOG]开始请求URL[{}],参数为:{}", url, parameters);

            StopWatch stopWatch = new StopWatch();
            invokeTimeTL.set(stopWatch);
            stopWatch.start();
            return true;
        }
        return false;
    }

    @Override
    public void postHandleByHandlerMethod(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletionByHandlerMethod(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (TLogContext.enableInvokeTimePrint()) {
            StopWatch stopWatch = invokeTimeTL.get();
            stopWatch.stop();
            log.info("[TLOG]结束URL[{}]的调用,耗时为:{}毫秒", request.getRequestURI(), stopWatch.getTime());
            invokeTimeTL.remove();
        }
    }
}
