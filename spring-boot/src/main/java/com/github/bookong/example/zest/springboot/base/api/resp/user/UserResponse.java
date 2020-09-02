package com.github.bookong.example.zest.springboot.base.api.resp.user;

import com.github.bookong.example.zest.springboot.base.api.resp.BaseResponse;
import com.github.bookong.example.zest.springboot.base.mybatis.entity.User;

import java.util.Date;

/**
 * @author Jiang Xu
 */
public class UserResponse extends BaseResponse {

    private Long   userId;

    private String loginName;

    private String nickname;

    private Date   createTime;

    public UserResponse(){
        super();
    }

    public UserResponse(User user){
        super();
        this.userId = user.getId();
        this.loginName = user.getLoginName();
        this.nickname = user.getNickname();
        this.createTime = user.getCreateTime();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
