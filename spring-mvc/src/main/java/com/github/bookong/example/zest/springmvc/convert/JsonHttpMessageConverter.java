package com.github.bookong.example.zest.springmvc.convert;

import com.github.bookong.example.zest.springmvc.common.GlobalConstant;
import com.github.bookong.example.zest.springmvc.util.JsonUtil;
import com.github.bookong.example.zest.springmvc.util.ResponseUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;

public class JsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return JsonUtil.fromJson(StreamUtils.copyToString(inputMessage.getBody(), GlobalConstant.Encoding.UTF8), clazz);
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        HttpHeaders headers = outputMessage.getHeaders();
        String jsonStr = JsonUtil.toJson(obj);
        int len = jsonStr.getBytes(GlobalConstant.Encoding.UTF8).length;

        headers.setContentLength(len);
        ResponseUtil.writeString(outputMessage.getBody(), jsonStr);
    }
}
