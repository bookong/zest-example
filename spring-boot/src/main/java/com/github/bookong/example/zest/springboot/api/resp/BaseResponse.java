package com.github.bookong.example.zest.springboot.api.resp;

import com.github.bookong.example.zest.springboot.base.enums.ApiStatus;
import com.github.bookong.example.zest.springboot.exception.ApiException;

/**
 * @author Jiang Xu
 */
public class BaseResponse {

    private int    code = ApiStatus.OK.getCode();
    private String msg  = ApiStatus.OK.getMessage();

    public BaseResponse(){
        super();
    }

    public BaseResponse(ApiException e){
        this(e.getStatus().getCode(), e.getMessage());
    }

    public BaseResponse(ApiStatus status){
        this(status.getCode(), status.getMessage());
    }

    public BaseResponse(int code, String msg){
        super();
        this.code = code;
        this.msg = msg;
    }

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
