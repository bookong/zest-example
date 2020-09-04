package com.github.bookong.example.zest.springmvc.base.api.resp.user;

import com.github.bookong.example.zest.springmvc.base.api.resp.BaseResponse;
import com.github.bookong.example.zest.springmvc.base.enums.ApiStatus;
import com.github.bookong.example.zest.springmvc.base.mongo.entity.ComplexUser;
import com.github.bookong.example.zest.springmvc.base.mongo.entity.SimpleUser;
import com.github.bookong.example.zest.springmvc.base.mybatis.entity.User;
import com.github.bookong.example.zest.springmvc.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiang Xu
 */
public class UserOneDayResponse extends BaseResponse {

    private List<UserResponse> users = new ArrayList<>();

    public UserOneDayResponse(){
        super();
    }

    public UserOneDayResponse(List<?> list){
        super();
        for (Object obj : list) {
            if (obj instanceof User) {
                getUsers().add(new UserResponse((User) obj));
            } else if (obj instanceof SimpleUser) {
                getUsers().add(new UserResponse((SimpleUser) obj));
            } else if (obj instanceof ComplexUser) {
                getUsers().add(new UserResponse((ComplexUser) obj));
            } else {
                throw new ApiException(ApiStatus.SYSTEM_ERROR, String.format("unknown object %s", obj.getClass().getName()));
            }
        }
    }

    public List<UserResponse> getUsers() {
        return users;
    }
}
