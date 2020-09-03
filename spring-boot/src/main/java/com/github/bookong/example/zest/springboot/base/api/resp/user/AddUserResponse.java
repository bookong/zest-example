package com.github.bookong.example.zest.springboot.base.api.resp.user;

import com.github.bookong.example.zest.springboot.base.api.resp.BaseResponse;

/**
 * @author Jiang Xu
 */
public class AddUserResponse extends BaseResponse {

    private Long id;

    public AddUserResponse(){
        super();
    }

    public AddUserResponse(Long id){
        super();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
