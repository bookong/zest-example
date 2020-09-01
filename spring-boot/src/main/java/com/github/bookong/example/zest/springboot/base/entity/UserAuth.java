package com.github.bookong.example.zest.springboot.base.entity;

import java.util.Date;

/**
 * @author Jiang Xu
 */
public class UserAuth {

    private Long   id;

    private Long   userId;

    private String auth;

    private Date   expirationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }
}
