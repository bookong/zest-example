package com.github.bookong.example.zest.springboot.base.enums;

/**
 * @author Jiang Xu
 */
public enum ApiStatus {
                       OK(0, ""),

                       SYSTEM_ERROR(1, "system error"),

                       DATA_ACCESS_ERROR(3, "Data access exception"),

                       FAIL_PARSE_PARAM(4, "Failed to parse parameters"),

                       PARAM_ERROR(5, "Parameter error");

    private int    code;
    private String message;

    ApiStatus(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
