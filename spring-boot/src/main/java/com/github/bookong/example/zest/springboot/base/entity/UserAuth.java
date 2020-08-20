package com.github.bookong.example.zest.springboot.base.entity;

/**
 * @author Jiang Xu
 */
public class UserAuth {

    private Long   id;

    private Long   userId;

    private String auth;

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
}
