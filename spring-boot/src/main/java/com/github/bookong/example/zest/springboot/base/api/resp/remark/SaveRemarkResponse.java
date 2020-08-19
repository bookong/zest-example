package com.github.bookong.example.zest.springboot.base.api.resp.remark;

import com.github.bookong.example.zest.springboot.base.api.resp.BaseResponse;

/**
 * @author Jiang Xu
 */
public class SaveRemarkResponse extends BaseResponse {

    private String id;

    public SaveRemarkResponse(){
        super();
    }

    public SaveRemarkResponse(String id){
        super();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
