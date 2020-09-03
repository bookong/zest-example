package com.github.bookong.example.zest.springboot.base.api.resp.user;

import com.github.bookong.example.zest.springboot.base.mongo.entity.ComplexUser;
import com.github.bookong.example.zest.springboot.base.mongo.entity.SimpleUser;
import com.github.bookong.example.zest.springboot.base.mybatis.entity.User;

import java.text.SimpleDateFormat;

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
