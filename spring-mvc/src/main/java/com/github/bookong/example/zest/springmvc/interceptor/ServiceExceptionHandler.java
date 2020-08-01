package com.github.bookong.example.zest.springmvc.interceptor;

import com.github.bookong.example.zest.springmvc.util.JsonUtil;
import com.github.bookong.example.zest.springmvc.util.ResponseUtil;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ServiceExceptionHandler implements HandlerExceptionResolver, Ordered {

    protected static final Logger logger = LoggerFactory.getLogger(ServiceExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exp) {
        // Class<?> retRespClass = RetResp.class; // TODO
        Class<?> retRespClass = Object.class;
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            retRespClass = hm.getMethod().getReturnType();
        }

        ModelAndView mv = new ModelAndView();
        String jsonStr = "";

        try {
            jsonStr = JsonUtil.toJson(retRespClass.newInstance());
        } catch (Exception e) {
            // RetResp retResp = new RetResp();
            // retResp.init(RetInfo.SYSTEM_ERROR, null, request);
            // jsonStr = JsonUtil.toJsonString(new ApiResponse(retResp));
            logger.error("", e);
        }

        ExpView expView = new ExpView(jsonStr);
        mv.setView(expView);
        return mv;
    }

    private static class ExpView implements View {

        private String jsonStr;

        ExpView(String jsonStr){
            this.jsonStr = jsonStr;
        }

        @Override
        public String getContentType() {
            return ResponseUtil.CONTENT_TYPE_JSON;
        }

        @Override
        public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
            response.setContentType(ResponseUtil.CONTENT_TYPE_JSON);
            ResponseUtil.writeString(response, jsonStr);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
