package com.github.bookong.example.zest.springboot.base.mongo.entity;

import java.util.Date;

/**
 * @author Jiang Xu
 */
public class Auth {

    private String auth;

    private Date   expirationTime;

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
