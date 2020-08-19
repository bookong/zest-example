package com.github.bookong.example.zest.springboot.base.api.param.user;

import javax.validation.constraints.NotEmpty;

/**
 * @author Jiang Xu
 */
public class UserExtInfoParam {

    @NotEmpty(message = "key cannot be empty")
    private String key;

    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
