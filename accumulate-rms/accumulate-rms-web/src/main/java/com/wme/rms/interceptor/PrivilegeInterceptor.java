package com.wme.rms.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Wangmingen on 2015/9/14.
 */
public class PrivilegeInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        /*LoginUser user = (LoginUser) request.getSession().getAttribute("loginUser");
        if (user == null) {
            response.sendRedirect("/login");
            return false;
        }*/
        return true;
    }
}
