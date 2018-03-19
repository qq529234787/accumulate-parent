package com.wme.rms.interceptor;

import com.wme.base.utils.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by admin on 2015/5/21.
 */
public class ParameterInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof ResourceHttpRequestHandler) return true;
        String remoteIp = request.getHeader("X-Real-IP");
        if (remoteIp == null) {
            remoteIp = request.getRemoteAddr();
        }
        String uri = request.getRequestURI();
        String method = request.getMethod();
        if (logger.isInfoEnabled()) {
            logger.info("remoteIp {} requested {} using {} method with parameters {}", remoteIp, uri, method,
                    StrUtils.toJson(request.getParameterMap()));
        }
        return true;
    }
}
