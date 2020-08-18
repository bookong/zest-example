package com.github.bookong.example.zest.springboot.exception;

import com.github.bookong.example.zest.springboot.base.enums.ApiStatus;

/**
 * @author Jiang Xu
 */
public class ApiException extends RuntimeException {

    private ApiStatus status;

    public ApiException(ApiStatus status){
        super(status.getMessage());
        this.status = status;
    }

    public ApiException(ApiStatus status, String message){
        super(status.getMessage().concat("(").concat(message).concat(")"));
        this.status = status;
    }

    public ApiStatus getStatus() {
        return status;
    }
}
