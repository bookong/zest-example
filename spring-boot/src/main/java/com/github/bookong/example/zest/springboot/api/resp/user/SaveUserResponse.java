package com.github.bookong.example.zest.springboot.api.resp.user;

import com.github.bookong.example.zest.springboot.api.resp.BaseResponse;

/**
 * @author Jiang Xu
 */
public class SaveUserResponse extends BaseResponse {

    private Long id;

    public SaveUserResponse(){
        super();
    }

    public SaveUserResponse(Long id){
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
