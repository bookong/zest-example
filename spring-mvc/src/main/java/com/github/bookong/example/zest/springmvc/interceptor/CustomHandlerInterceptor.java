package com.github.bookong.example.zest.springmvc.interceptor;

import com.github.bookong.example.zest.springmvc.base.enums.ApiStatus;
import com.github.bookong.example.zest.springmvc.exception.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomHandlerInterceptor implements HandlerInterceptor {

    @Value("${zestdemo.token}")
    private String zestdemoToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = StringUtils.trimToEmpty(request.getParameter("token"));
        if (!StringUtils.equals(zestdemoToken, token)) {
            throw new ApiException(ApiStatus.PARAM_ERROR, "The token parameter is incorrect");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
