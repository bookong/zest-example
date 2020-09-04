package com.github.bookong.example.zest.springmvc.base.api.param.user;

import javax.validation.constraints.NotEmpty;

/**
 * @author Jiang Xu
 */
public class UserParam {

    @NotEmpty(message = "loginName cannot be empty")
    private String loginName;

    @NotEmpty(message = "password cannot be empty")
    private String password;

    @NotEmpty(message = "nickname cannot be empty")
    private String nickname;

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
