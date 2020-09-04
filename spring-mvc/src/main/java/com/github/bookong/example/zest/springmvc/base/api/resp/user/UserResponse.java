package com.github.bookong.example.zest.springmvc.base.api.resp.user;

import com.github.bookong.example.zest.springmvc.base.mongo.entity.ComplexUser;
import com.github.bookong.example.zest.springmvc.base.mongo.entity.SimpleUser;
import com.github.bookong.example.zest.springmvc.base.mybatis.entity.User;

/**
 * @author Jiang Xu
 */
public class UserResponse {

    private String nickname;

    public UserResponse(){
    }

    public UserResponse(User user){
        this.nickname = user.getNickname();
    }

    public UserResponse(SimpleUser user){
        this.nickname = user.getNickname();
    }

    public UserResponse(ComplexUser user){
        this.nickname = user.getNickname();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
