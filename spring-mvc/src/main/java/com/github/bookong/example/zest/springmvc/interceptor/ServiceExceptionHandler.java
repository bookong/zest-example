package com.github.bookong.example.zest.springmvc.interceptor;

import com.github.bookong.example.zest.springmvc.base.api.resp.BaseResponse;
import com.github.bookong.example.zest.springmvc.base.enums.ApiStatus;
import com.github.bookong.example.zest.springmvc.exception.ApiException;
import com.github.bookong.example.zest.springmvc.util.JsonUtil;
import com.github.bookong.example.zest.springmvc.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ServiceExceptionHandler implements HandlerExceptionResolver, Ordered {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        String methodName = StringUtils.EMPTY;
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            methodName = hm.getMethod().getName();
        }

        BaseResponse resp = null;
        if (e instanceof ApiException) {
            resp = new BaseResponse((ApiException) e);

        } else if (e instanceof DataAccessException) {
            resp = new BaseResponse(ApiStatus.DATA_ACCESS_ERROR);
            logger.error(methodName, e);

        } else if (e instanceof HttpMessageConversionException) {
            resp = new BaseResponse(ApiStatus.FAIL_PARSE_PARAM);
            logger.error("{} : {}", methodName, e.getMessage());

        } else if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException notValidExp = (MethodArgumentNotValidException) e;
            List<ObjectError> allErrors = notValidExp.getBindingResult().getAllErrors();
            if (CollectionUtils.isEmpty(allErrors)) {
                logger.error(methodName, e);
            } else {
                String msg = allErrors.get(allErrors.size() - 1).getDefaultMessage();
                resp = new BaseResponse(new ApiException(ApiStatus.PARAM_ERROR, msg));
            }

        } else {
            resp = new BaseResponse(ApiStatus.SYSTEM_ERROR);
            logger.error(methodName, e);
        }

        ModelAndView mv = new ModelAndView();
        ExpView expView = new ExpView(JsonUtil.toJson(resp));
        mv.setView(expView);
        return mv;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
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
}
