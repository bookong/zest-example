package com.github.bookong.example.zest.springboot.base.enums;

/**
 * @author Jiang Xu
 */
public enum ApiStatus {
                       OK(0, ""),

                       SYSTEM_ERROR(1, "system error"),

                       SQL_ERROR(3, "SQL execution exception"),

                       FAIL_PARSE_PARAM(4, "Failed to parse parameters"),

                       PARAM_ERROR(5, "Parameter error"),

                       CALL_OTHER_SYSTEM(6, "Failed to call other system");

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
