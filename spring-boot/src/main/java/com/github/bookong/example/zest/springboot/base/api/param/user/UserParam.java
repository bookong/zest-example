package com.github.bookong.example.zest.springboot.base.api.param.user;

import javax.validation.constraints.NotEmpty;

/**
 * @author Jiang Xu
 */
public class UserParam {

    private Long   id;

    @NotEmpty(message = "loginName cannot be empty")
    private String loginName;

    @NotEmpty(message = "password cannot be empty")
    private String password;

    @NotEmpty(message = "nickname cannot be empty")
    private String nickname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
