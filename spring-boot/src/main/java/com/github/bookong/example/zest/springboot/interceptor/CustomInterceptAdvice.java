package com.github.bookong.example.zest.springboot.interceptor;

import com.github.bookong.example.zest.springboot.api.resp.BaseResponse;
import com.github.bookong.example.zest.springboot.base.enums.ApiStatus;
import com.github.bookong.example.zest.springboot.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;

/**
 * @author Jiang Xu
 */
@ControllerAdvice
public class CustomInterceptAdvice {

    private static final Logger logger = LoggerFactory.getLogger(CustomInterceptAdvice.class);

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody BaseResponse handle(Exception e, HandlerMethod m) {
        logger.error(m.getMethod().getName(), e);

        BaseResponse resp = null;
        if (e instanceof ApiException) {
            resp = new BaseResponse((ApiException) e);
        } else if (e instanceof DataAccessException) {
            resp = new BaseResponse(ApiStatus.SQL_ERROR);
        } else if (e instanceof HttpMessageConversionException) {
            resp = new BaseResponse(ApiStatus.FAIL_PARSE_PARAM);
        }

        if (resp == null) {
            resp = new BaseResponse(ApiStatus.SYSTEM_ERROR);
        }

        return resp;
    }
}
