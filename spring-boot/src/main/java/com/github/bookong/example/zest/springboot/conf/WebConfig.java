package com.github.bookong.example.zest.springboot.conf;

import com.github.bookong.example.zest.springboot.interceptor.CustomHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author Jiang Xu
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private CustomHandlerInterceptor customHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customHandlerInterceptor).addPathPatterns("/**");
    }
}
