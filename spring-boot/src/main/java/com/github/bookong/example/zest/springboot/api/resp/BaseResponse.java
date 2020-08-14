package com.github.bookong.example.zest.springboot.api.resp;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Jiang Xu
 */
public class BaseResponse {

    private int    code = 0;
    private String msg  = StringUtils.EMPTY;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
