package com.github.bookong.example.zest.springboot.interceptor;

import com.github.bookong.example.zest.springboot.base.enums.ApiStatus;
import com.github.bookong.example.zest.springboot.exception.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jiang Xu
 */
@Component
public class CustomHandlerInterceptor implements HandlerInterceptor {

    private @Value("${zestdemo.api.token}") String apiToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = StringUtils.trimToEmpty(request.getParameter("token"));
        if (!StringUtils.equals(apiToken, token)) {
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
