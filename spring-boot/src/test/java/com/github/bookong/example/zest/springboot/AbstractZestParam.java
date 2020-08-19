package com.github.bookong.example.zest.springboot;

import com.github.bookong.example.zest.springboot.base.api.resp.BaseResponse;
import com.github.bookong.zest.annotation.ZestConnection;
import com.github.bookong.zest.testcase.ZestParam;
import com.github.bookong.zest.util.ZestJsonUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;

/**
 * @author Jiang Xu
 */
public abstract class AbstractZestParam<T extends BaseResponse> implements ZestParam {

    @ZestConnection("mysql")
    public Connection conn;
    public String     apiToken;
    private T         expectedObj;
    private String    apiExpected;

    protected String makeUrl(String url) {
        return StringUtils.isBlank(apiToken) ? url : String.format("%s?token=%s", url, apiToken);
    }

    public T getExpected() {
        if (expectedObj == null) {
            Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            expectedObj = ZestJsonUtil.fromJson(apiExpected, clazz);
        }
        return expectedObj;
    }
}
