package com.github.bookong.example.zest.springmvc.base.api.resp;

import com.github.bookong.example.zest.springmvc.base.enums.ApiStatus;
import com.github.bookong.example.zest.springmvc.exception.ApiException;

/**
 * @author Jiang Xu
 */
public class BaseResponse {

    public static BaseResponse OK = new BaseResponse();

    private int                code;
    private String             msg;

    public BaseResponse(){
        this(ApiStatus.OK);
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
